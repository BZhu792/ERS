@ECHO OFF
if not DEFINED IS_MINIMIZED set IS_MINIMIZED=1 && start "ERS" /min "%cd%/ERS.cmd" %* && exit
echo Running...
echo Closing this window will terminate the game.

java -p "%cd%\openjfx-11.0.2_windows-x64_bin-sdk\javafx-sdk-11.0.2\lib" --add-modules javafx.controls --add-modules javafx.base,javafx.graphics --add-reads javafx.base=ALL-UNNAMED --add-reads javafx.graphics=ALL-UNNAMED -Dfile.encoding=UTF-8 -classpath %cd%\out\production\ErsGame;%cd%\openjfx-11.0.2_windows-x64_bin-sdk\javafx-sdk-11.0.2\lib\src.zip;%cd%\openjfx-11.0.2_windows-x64_bin-sdk\javafx-sdk-11.0.2\lib\javafx-swt.jar;%cd%\openjfx-11.0.2_windows-x64_bin-sdk\javafx-sdk-11.0.2\lib\javafx.web.jar;%cd%\openjfx-11.0.2_windows-x64_bin-sdk\javafx-sdk-11.0.2\lib\javafx.base.jar;%cd%\openjfx-11.0.2_windows-x64_bin-sdk\javafx-sdk-11.0.2\lib\javafx.fxml.jar;%cd%\openjfx-11.0.2_windows-x64_bin-sdk\javafx-sdk-11.0.2\lib\javafx.media.jar;%cd%\openjfx-11.0.2_windows-x64_bin-sdk\javafx-sdk-11.0.2\lib\javafx.swing.jar;%cd%\openjfx-11.0.2_windows-x64_bin-sdk\javafx-sdk-11.0.2\lib\javafx.controls.jar;%cd%\openjfx-11.0.2_windows-x64_bin-sdk\javafx-sdk-11.0.2\lib\javafx.graphics.jar Menu

start "ERSHelper" "%cd%/ERSHelper.cmd" && exit
