# FindRestaurant 

Getting it running <br />
Step 1: Download/Clone the repo from github. 
Step 2: Import the project using build.gradle file from Android Studio Import Project Window. 
Step 3: Download or sync any dependency that gradle will guide you through. 
Step 4: Play around

Notes: 
The apikey has limit of 1000 request per 24 hour, so if app is not showing you restaurant that might be the reson. 
I have provided another apiKey you can use to run the application. 
you have to changes in two place. 
  .../FindRestaurant/app/src/debug/res/values/google_maps_api.xml
  .../FindRestaurant/app/src/main/java/com/jagrutdesai/findrestaurant/network/RetrofitClient.java
  

Tested On Device: 
Samsung Galaxy 6 
Samsung Galaxy 8
