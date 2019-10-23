VeeqoEmailer

/rno 
loads stored lastId
reads orders from veeqo from that id
iterates through each tag that has been set (currrently b&q,homebase,argos)
reads each order and uploads it as stage 0
also sends a email and text for that order, to acknowledge order receipt

/fu
iterates through each order thats hasnt been shipped completly or cancelled (stage 0)
if there are any shipments an email and text is sent to the customer
if the order has been shipped completly it is changed to stage 1
if there are any errors (such as missing field or cancelled) it is moved to stage 2

/ds
debug servlet, shows the stage of orders

/ss
the servlet sets the lastID to one quite far is the past
if you wish to change the program comment Texter and Emailer
then run /ss and then /rno and /fu. 
Dont run this without commenting out /rno and /fu or you will email all the old orders

APIKEYCreator
run this to create a file called api.java, it will read a json file with veeqoAPI, sendGridApi, twilioSid, twilioToken

Cron Job
this has been uploaded to google app engine with a cron job that will run /rno and /fu every 30 minutes



