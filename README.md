# SMART-GH Android Application

This is the repo for Group 17's CS2013 Software Engineering Project.
It is an Android Application of the SMART-GH Web-application (https://github.com/DIVERSIFY-project/SMART-GH).

# How to install

Begin by following the instructions to install and run the SMART-GH Web-application here: https://github.com/DIVERSIFY-project/SMART-GH/blob/master/docs/How-to-install-SMART-GH

We recommend you use Android Studio (At least 1.1.0) to install and run project in.

Clone this repo into a new project and the application should install and run without any problems. 
Use a Virtual Device supporting at least Android 5.0.

Now your app will be able to mark any route in the greater Dublin Region!

## Cloning a repository in Android Studio
Open Android Studio, and select "Checkout project from Version Control". Select Github from the list, and enter in your login details. Enter the repository url, and finish the wizard. The repository will now be imported into your workspace.

*If the maps do not work for you, ensure your Android Virtual Device uses an SD with enough memory*

# How to use

Firstly, touch on the gear on the top right of the first screen and enter the server address to be the url of the web-app that should still be running locally + "/route/".

<div>
  <p>
    <img src="/HowToUse/UrlSelectSetting.png" width="200"/>
    &nbsp;&nbsp;&nbsp;
    <img src="/HowToUse/UrlSelectSettingInput.png" width="200"/>
  </p>
</div>

Enter the relevant details in the main screen, the app only works for the Greater Dublin region.
Selecting "View Map" will show you your selected "To" and "From" points on an interactive map of Dublin.

<div>
  <p>
    <img src="/HowToUse/MainScreen.png" width="200"/>
    &nbsp;&nbsp;&nbsp;
    <img src="/HowToUse/MapSelectScreen.png" width="225"/>
  </p>
</div>
Hit "Search" and you will be able to see the route you requested, hit "View On Map" to see it on an interactive map of Dublin.

<div>
  <p>
    <img src="/HowToUse/InfoScreen.png" width="200"/>
    &nbsp;&nbsp;&nbsp;
    <img src="/HowToUse/LongMapRouteScreen.png" width="215"/>
  </p>
</div>

Use the back button on your Android device to return to the main screen and start a new request.
