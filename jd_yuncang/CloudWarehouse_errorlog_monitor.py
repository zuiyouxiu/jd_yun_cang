#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# date: 2021-03-23
# des：提取京东物流基础设施平台（云仓）异常日志中等级>3的日志，写入excel中，并自动邮件发送给相关人员。
# des: 已由excel改为html格式
# des：数据库密码已改成自动循环尝试，避免密码修改后导致连接失败

import logging
import xlsxwriter
import os
import pymysql
import configparser
import datetime
import smtplib 
from email.header import Header
from email.mime.text import MIMEText
import argparse
import random
import string

def qurey_log(old_maxID, email_password):
	#根据规定，该mysql用户密码每三个月需修改一次，每次将末尾的数字加1
	#为避免数据库密码被改后该脚本忘了同步更新导致连接失败，采用循环拼接密码的方式尝试连接
	 password_null = 'YOy57!6-sdoMc@a',
	 for i in range(0, 9):
      password_temp = password_null + str(i)
	    try:
	    	conn = pymysql.connect(
	        host = '114.67.75.35',
	        port = 3306,
	        user = 'yunc',
	        password = password_temp,
	        database = 'CloudWarehouseDB',
	        charset = 'utf8'
		    cursor = conn.cursor(cursor=pymysql.cursors.DictCursor)
	    	print("已连接数据库：", db)
	    	set_log("connect mysql successful")
			except pymysql.err.OperationalError as oe:
		    set_log("connect mysql failed", oe)
		    break     

	    old_maxID = str(old_maxID)

	    sql2 = 'select max(ID) from warehouse_temp'
	    cursor.execute(sql2)
	    result = cursor.fetchone()
	    temp_maxID = result
	    temp_maxID = str(temp_maxID)
	    temp_maxID = temp_maxID[1:]
	    temp_maxID = temp_maxID[:-2]
	    temp_maxID = int(temp_maxID)
	    deal_num = temp_maxID - int(old_maxID)
	    temp_deal_num = deal_num
	    deal_num = str(deal_num)
	    set_log('deal_num is ' + deal_num)
	    set_log('new max num is ' + str(temp_maxID))
	    temp_n = 0
	    this_body = ""
	    if(temp_deal_num > 0):
	        sql = "select t1.id,t1.date,t1.userID,t1.error_type,t1.error_level,t1.error_content,t1.error_func,t1.action from warehouse_error_log as t1 left join error_type as t2 on t1.error_type = t2.type_id where id > " + old_maxID + " and t1.error_level > 3"
	        print (sql)
	        cursor.execute(sql)
	        result = cursor.fetchall()
	        #cursor.scroll(0,mode='absolute')
	        
	        fields = cursor.description
	        
	        temp_n = write_2_excel(fields,result)
	        this_body = set_mail_body(fields,result)
	    cursor.close()
	    con.close()

	    # seed_mail(temp_n,deal_num)
	    
	    return temp_maxID

def write_2_excel(fields,result):
    temp_line = ""
    set_log("start write excel")
    temp_date = datetime.datetime.now().strftime('%Y-%m-%d')
    temp_date = str(temp_date)
    xlsx_name = "errorlog_data_" + temp_date + ".xlsx"
    workbook = xlsxwriter.Workbook(xlsx_name)
    worksheet = workbook.add_worksheet()
    worksheet.set_column("B:B", 50)
    worksheet.set_column("O:O", 50)
    worksheet.set_column("D:D", 15)
    worksheet.set_column("J:J", 15)
    row = 0
    worksheet.write(row,0,u'日志ID')
    worksheet.write(row,1,u'写入时间')
    worksheet.write(row,2,u'写入用户ID')
    worksheet.write(row,3,u'错误类型')
    worksheet.write(row,4,u'错误级别')
    worksheet.write(row,5,u'日志详情')
    worksheet.write(row,6,u'报错函数')
    worksheet.write(row,7,u'报错动作')
    temp_n = 0
    for row in range(1,len(result)+1):
        temp_n = temp_n + 1
        for col in range(len(fields)):
            worksheet.write(row,col,result[row-1][col])
    workbook.close()
    set_log("write excel succ")
    return temp_n

def set_mail_body(fields,result):
    temp_line = ""
    set_log("start set mail body")
    for row in range(1,len(result)+1):
        temp_line = temp_line + """<tr> <td><font size="3" color="blue">编号：""" + result[row-1][3] + """<br /></font></td>  </tr>"""
        temp_line = temp_line + """<tr> <td>""" + result[row-1][9] + """<br /></font></td>  </tr>"""
        temp_line = temp_line + """<tr> <td>""" + result[row-1][10] + """<br /></font></td>  </tr>"""
        temp_line = temp_line + """<tr> <td>""" + result[row-1][4] + """<br /></font></td>  </tr>    <tr><td>*******************************************************************************************<br /><br /></td></tr>"""
    return temp_line

def seed_mail(row,dn):
    set_log("start seed mail")
    row = str(row)
    temp_cmd = "python2 auto_seed_mail.py -n " + row + " -d " + dn
    print (temp_cmd)
    temp_cmd = str(temp_cmd)
    os.system(temp_cmd)
    
def new_seed_mail(row,dn,mail_body,log_num,password,sender,receiver):
    set_log("start seed mail")
    row = int(row)
    # temp_cmd = "python2 auto_seed_mail.py -n " + row + " -d " + dn
		# receiver = ['jdcloud-xxxxx@jd.com']
    smtpserver = 'mx.jd.local'
    username = 'warehouse'

    # 邮件主题 
    if row > 0:
        mail_title = '今日发现了关注的严重错误日志，请分析确认'
    else:
        mail_title = '今日暂未发现关注的严重错误日志'
        mail_body = ""

    text = "本次从错误日志中的 " + str(dn) + " 条日志中提取了 " + str(log_num) + " 条严重错误日志。<br />From auto script.<br /><br />"
    text = """<tr> <td>""" + text + """<br /></font></td>  </tr>"""
    mail_body = text + mail_body
    text = "<br />###########################################################################################<br />"
    text = text + "该邮件来至 云仓 自动监测，请勿直接回复。<br />如有疑问请联系云仓同学处理。"
    text = """<tr> <td>""" + text + """<br /></font></td>  </tr>"""
    mail_body = mail_body + text
    mail_body = """<html>   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />    <table color="CCCC33" width="800" border="2" cellspacing="1" text-align="center">""" + mail_body + """</table> </html>"""
    print ("----------------------------------------\n")
    # print (mail_body)
    mail_body = mail_body

    message = MIMEText(mail_body, 'html', 'utf-8')
    message['From'] = Header(u'京东物流云仓日志', 'utf-8').encode()

    message['Subject'] = Header(mail_title, 'utf-8')
    message['To'] = ";".join(receiver)
    res = 0

    try:
        smtp = smtplib.SMTP()
        smtp.connect(smtpserver)
        smtp.login(username, password)
        smtp.sendmail(sender, receiver, message.as_string())
        print("发送邮件成功！！！")
        res = 1
        set_log('seed mail succ ')
        smtp.quit()
    except smtplib.SMTPException:
        print("发送邮件失败！！！")
        set_log('seed mail failed !!')
    return res


def set_log(str_log):
    logging.basicConfig(filename='log.txt', level=logging.INFO,format=' %(asctime)s - %(levelname)s - %(message)s')
    #logging.basicConfig(level=logging.INFO, format=' %(asctime)s - %(levelname)s - %(message)s')

    if os.path.exists('log.txt'):
        print ("Have found the log.txt")
        
    else:
        logging.info("/home/mission/warehouse/log.txt is not existed!!!")
    logging.info(str_log)

def config():
    now_dir = os.getcwd()
    os.chdir('/home/mission/warehouse/')
    set_log('dir is  ' + now_dir)
    now_dir = os.getcwd()
    set_log('dir is  ' + now_dir)

    set_log("start get para")
    cf = configparser.ConfigParser()
    set_log("start get config.ini")
    cf.read("config.ini")
    conf_password = cf.get("Mail-para", "password")
    conf_sender = cf.get("Mail-para", "sender")
    conf_receiver = cf.get("Mail-receiver", "receiver")
    old_maxID = cf.get("ID", "maxID")
    set_log('old maxID is ' + old_maxID)
    print (old_maxID)
 
    temp_maxID = qurey_log(old_maxID)
    
    temp_maxID = str(temp_maxID)
    print (temp_maxID)
    cf.set("ID","maxID",temp_maxID)
    # set_log('new maxID is ' + temp_maxID)
    set_log("start update config.ini")
    with open("config.ini","w+") as f:
        cf.write(f)
    f.close();
