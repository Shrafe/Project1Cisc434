@echo off
SET /p scenario=Enter the Scenario:
SET /p ip=Enter the server ip:
SET hostname=http://%ip%
SET /p port=Enter the server port:

ECHO Scenario is: %scenario%
ECHO Hostname is: %hostname%
ECHO Port is: %port%

FOR /L %%G IN (0,1,9) DO (
	start java -jar RpcClient.jar %scenario% %hostname% %port% %%G
	)
pause


