@ECHO OFF
REM 绝对ROOT
SET ROOTPATH=%~dp0
REM SIGNED 目录
SET SIGNEDPATH=signed
REM RESIGNED 目录
SET RESIGNEDPATH=resigned
REM key的名称
SET KEYSTORE_NAME=app.keystore
REM key的别名
SET KEYSTORE_ALIAS=first_app_key_store
REM key的密码
SET KEYSTORE_STOREPASS=ww0445465674
SET KEYSTORE_KEYPASS=ww0445465674
REM 临时文件名或临时文件夹名
SET TEMP=temp

MD %ROOTPATH%%TEMP%
FOR %%I IN (%SIGNEDPATH%/*.apk) DO (
	 ECHO starting resigning apk file - %%I ...    
     
     MD %ROOTPATH%%%I
	
	 rem ECHO copying %ROOTPATH%%SIGNEDPATH%%%I
     COPY /y %ROOTPATH%%SIGNEDPATH%\%%I %ROOTPATH%%TEMP%\%%I
     REM 复制key到APK同名文件夹中
     COPY %ROOTPATH%%KEYSTORE_NAME% %ROOTPATH%%%I\%KEYSTORE_NAME%
	 
	 CD %ROOTPATH%%TEMP%
	 
	 ECHO unziping %%I ...
	 REM USE -xvf IF NEED DETAILS
	 JAR -xf %ROOTPATH%%TEMP%\%%I
	 DEL %ROOTPATH%%TEMP%\%%I
	 ECHO unziping %%I done
	 
	 RD /S /Q %ROOTPATH%%TEMP%\META-INF
	 
	 ECHO generating unsigned apk %%I
	 REM USE -xvf IF NEED DETAILS
	 JAR -cf %ROOTPATH%%%I\%%I ./
	 CD ..
	 ECHO generating unsigned apk %%I done
	 
	 ECHO signing using new key apk %%I ...
	 REM -VERBOSE IF NEED PRINTING DETAILS
	 JARSIGNER -KEYSTORE %KEYSTORE_NAME% -STOREPASS %KEYSTORE_STOREPASS% %ROOTPATH%%%I\%%I %KEYSTORE_ALIAS% -KEYPASS %KEYSTORE_KEYPASS%	 
	 
	 REM VERIFY
	 REM JARSIGNER -VERBOSE -VERIFY %ROOTPATH%%%I\%%I
	 ECHO signing using new key apk %%I done
	 
	 IF EXIST %ROOTPATH%%RESIGNEDPATH%\%%I (
		DEL %ROOTPATH%%RESIGNEDPATH%\%%I
	 )
	 
	 ECHO zipaligning new apk %%I ...
	 REM -v IF NEED DETAILS
	 ZIPALIGN 4 %ROOTPATH%%%I\%%I %ROOTPATH%%RESIGNEDPATH%\%%I
	 
	 REM VERIFY -v IF NEED DETAILS
	 REM ZIPALIGN -c 4 %ROOTPATH%%RESIGNEDPATH%\%%I
	 ECHO zipaligning new apk %%I done
	 
	 RD /S /Q %ROOTPATH%%%I
	 ECHO resigning apk file - %%I done
)

RD /S /Q %ROOTPATH%%TEMP%