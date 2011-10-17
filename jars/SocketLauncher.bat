@echo off
SET /p scenario=Enter the Scenario:
SET /p hostname=Enter the server hostname:
SET /p port=Enter the server port: 

ECHO Scenario is: %scenario%
ECHO Hostname is: %hostname%
ECHO Port is: %port%

FOR /L %%G in (0,1,9) DO (
	start cmd /c java -jar SocketClient.jar %scenario% %port% %hostname%
	)
pause
	   


