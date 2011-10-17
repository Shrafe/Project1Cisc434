@echo off
SET /p scenario=Enter the Scenario:

ECHO The Scenario is: %scenario%

FOR /L %%G in (0,1,9) DO (
	start cmd /c java -jar CorbaClient.jar %scenario% %%G 
	)
pause


