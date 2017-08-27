# FindRestaurant 

Getting it running <br />
Step 1: Download/Clone the repo from github. <br />
Step 2: Import the project using build.gradle file from Android Studio Import Project Window. <br />
Step 3: Download or sync any dependency that gradle will guide you through. <br />
Step 4: Play around<br />

Notes: <br />
The apikey has limit of 1000 request per 24 hour, so if app is not showing you restaurant that might be the reson. <br />
I have provided another apiKey you can use to run the application. <br />
you have to changes in two place. <br />
  .../FindRestaurant/app/src/debug/res/values/google_maps_api.xml <br />
  .../FindRestaurant/app/src/main/java/com/jagrutdesai/findrestaurant/network/RetrofitClient.java <br />
  

Tested On Device: <br />
Samsung Galaxy 6 <br />
Samsung Galaxy 8 <br />
