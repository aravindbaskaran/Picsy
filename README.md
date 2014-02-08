Picsy
=====

A clean private photo-sharing app for sharing photos among friends!

Cross platform mobile app developed using Mowbly(http://www.mowbly.com) and cloud services running out of a google app engine app

Developed as part of a workshop conducted at Pondicherry Engineering College (www.pec.edu) with the 3rd year CSE students on 7-8 February 2014.


How to deploy
============

###Mobile app

1. Create a free dev account at http://wwww.mowbly.com and import the picsy-mobile project as a zip to the Mowbly Cloud IDE
2. Change the _url variable in **picsy.js** to your app engine url hosting the services from picsy-cloud project
3. Open **MyPicsy.pack** from the project file explorer and click on **Pack**
4. Download **Mowbly** app from app store on any mobile OS(Android, iOS, Windows Phone, BB10)
5. Login with your account on the mobile to view the **MyPicsy** on your mobile!

Note: You can also view the app as a webapp by going to http://wwww.mowbly.com/webapp :)

Secret Note ;) The app has optional offline mode. To use it, just uncomment relevant code.

###GAE

1. Create an app ID from http://www.appengine.google.com
2. Import the picsy-cloud project to your Eclipse workspace
3. Deploy to App engine!

Find more readme under picsy-mobile and picsy-cloud folders

Happy snapping! :)
