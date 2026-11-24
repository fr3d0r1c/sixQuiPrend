@echo off
rem ============================================================
rem  lancer.bat : compile puis lance une partie du projet
rem  Utilisation : .\lancer.bat <cible> [arguments]
rem ============================================================

rem --- le chemin de JavaFX (a adapter si tu changes de version) ---
set FX=C:\javafx-sdk-27.0.1.1\lib
set FXOPT=--module-path "%FX%" --add-modules javafx.controls
set FXRUN=%FXOPT% --enable-native-access=javafx.graphics

rem --- on se place toujours a la racine du projet ---
cd /d "%~dp0"

if "%1"=="tests"    goto tests
if "%1"=="console"  goto console
if "%1"=="bench"    goto bench
if "%1"=="fx"       goto fx
if "%1"=="serveur"  goto serveur
if "%1"=="texte"    goto texte
if "%1"=="auto"     goto auto
if "%1"=="clientfx" goto clientfx
goto aide

:tests
javac -d out modele\*.java reseau\*.java test\*.java || goto erreur
java -cp out test.TestJeu
java -cp out test.TestProtocole
java -cp out test.TestEtatClient
goto fin

:console
javac -d out modele\*.java Application.java || goto erreur
java -cp out Application
goto fin

:bench
javac -d out modele\*.java BenchmarkBots.java || goto erreur
java -cp out BenchmarkBots
goto fin

:fx
javac %FXOPT% -d out modele\*.java vue\*.java ApplicationFX.java || goto erreur
copy /y style.css out\ >nul
java %FXRUN% -cp out ApplicationFX
goto fin

:serveur
rem exemple : .\lancer.bat serveur 2 3   (2 humains, 3 bots)
javac -d out modele\*.java reseau\*.java || goto erreur
java -cp out reseau.ServeurJeu %2 %3
goto fin

:texte
javac -d out modele\*.java reseau\*.java || goto erreur
java -cp out reseau.ClientTexte %2
goto fin

:auto
rem exemple : .\lancer.bat auto Robot1
javac -d out modele\*.java reseau\*.java || goto erreur
java -cp out reseau.ClientAuto %2
goto fin

:clientfx
javac %FXOPT% -d out modele\*.java reseau\*.java client\*.java || goto erreur
copy /y style.css out\ >nul
java %FXRUN% -cp out client.ConsoleReseauFX %2
goto fin

:aide
echo.
echo Utilisation : .\lancer.bat ^<cible^> [arguments]
echo.
echo   tests              tests unitaires (TestJeu, TestProtocole)
echo   console            le jeu en console
echo   bench              le benchmark des bots
echo   fx                 le jeu graphique en local
echo   serveur H B        le serveur de jeu (H humains, B bots)
echo   texte [adresse]    un client a la main
echo   auto PSEUDO        un client automatique
echo   clientfx           le client graphique en reseau
echo.
goto fin

:erreur
echo.
echo *** La compilation a echoue : rien n'a ete lance. ***

:fin