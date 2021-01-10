@ECHO OFF

MODE CON COLS=50 LINES=5

:again 
   set /p answer="Play Again (Y/N)? "
   if /i "%answer:~,1%" EQU "Y" set IS_MINIMIZED=1 && start "" /min "%cd%/ERS.cmd" %* && exit
   if /i "%answer:~,1%" EQU "N" EXIT
   echo "Please type Y for Yes or N for No"
   goto again