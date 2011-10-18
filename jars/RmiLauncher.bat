@echo off
SET /p scenario=Enter the Scenario:
SET /p hostname=Enter the server hostname:

ECHO Scenario is: %scenario%
ECHO Hostname is: %hostname%

FOR /L %%G in (0,1,9) DO (
	start java -Djava.security.policy=server.policy -jar RmiClient.jar %scenario% %hostname% %%G
	)  
pause


