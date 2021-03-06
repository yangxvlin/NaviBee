# NaviBee
[![All Contributors](https://img.shields.io/badge/all_contributors-5-orange.svg)](#contributors)
[![Github](https://img.shields.io/github/license/COMP30022-18/NaviBee.svg)](https://github.com/COMP30022-18/NaviBee/blob/master/LICENSE)
![GitHub repo size in bytes](https://img.shields.io/github/repo-size/COMP30022-18/NaviBee.svg)

NaviBee is a location-based Android smartphone app designed for elderly people, which is the group project of Unimelb COMP30022 IT Project.

The app is specifically designed for helping elderly people reach their destinations through sufficient location-based functionalities with concise UI.
One-to-one and one-to-many communications using rich media(text, image, voice call, location and real-time location) are also available for elderly people as extra help.
The function of Events and SOS are provided to users for social activities and emergency situation.  


### Screenshots
<table border="0">
 <tr>
    <td><img src=".github/1.jpg"!></td>
    <td><img src=".github/2.jpg"!></td>
    <td><img src=".github/3.jpg"!></td>
    <td><img src=".github/4.jpg"!></td>
    <td><img src=".github/5.jpg"!></td>
 </tr>
 <tr>
    <td><img src=".github/6.jpg"!></td>
    <td><img src=".github/7.jpg"!></td>
    <td><img src=".github/8.jpg"!></td>
    <td><img src=".github/9.jpg"!></td>
    <td><img src=".github/10.jpg"!></td>
 </tr>
</table>

### Key features
1. Location-based service
2. Rich media messaging(Text/Voice/Image)
3. Real-time location share
4. Public and private events
5. Emergency detection


## Technical details

### Folder Structure
```js
/
├── Android/    
│   │           // Android project root
│   ├── app/src/main/java/au/edu/unimelb/eng/navibee/
│   │           // Main code
│   └── app/src/test/java/au/edu/unimelb/eng/navibee/
│               // Unit test
├── Backend/
│               // Code for Firebase Cloud Function
└── Tool/
                // Code for migrating db during development
```

### Setup
NaviBee uses multiple online services. Please follow the instruction below in order to build your own version.

#### Firebase
1. Register [here](https://firebase.google.com/) and create a new project.
2. Enable Google Sign-in provider in Authentication.
3. Upload code in `\Backend\` to Firebase Cloud Function following [this guide](https://firebase.google.com/docs/functions/get-started).
4. Open the NaviBee Android project using Android Studio and connect it to the Firebase project following [this guide](https://firebase.google.com/docs/android/setup).

#### Agora
1. Register [here](https://www.agora.io/en/) and add the AppId to your `gradle.properties` like `agoraAppId=f******4`

#### Google Maps SDK
1. Follow the instructions [here](https://developers.google.com/places/android-sdk/signup) to obtain the key.
2. Add the key to your `gradle.properties` like `googlePlacesApiKey=5L+h44GY44Gf4-4KC44Gu44Gv6YO95ZCI44Gu44`

#### Mapbox
1. Obtain your API Token [here](https://www.mapbox.com/account/access-tokens).
2. Add the key to your `gradle.properties` like `mapboxApiToken=pk.GE44GE5aaE5oOz44KS57mw44KK6L+U44GX5pig44GX5Ye644GZ6Y+h5a6I44Gj4.4Gf44KC44Gu44Gv5p_iO44`

#### Here Maps
1. Obtain your application credentials [here](https://developer.here.com/documentation/android-premium/dev_guide/topics/credentials.html).
2. Add the keys to your `gradle.properties` like:
    ```
    hereAppId=KL44GE5pyq5p2l5bm75o
    hereAppCode=Oz44KS6KaL44Gb44Gq44GM
    hereLicenseKey=44KJ5raI44GI44Gm44KG44GP5YWJ5peF44Gu57WC44KP44KK44Gu5aSi44Gr6KaL44Gf5a2Y5Zyo44Gr5YOV44Gv44Gq44KM44G+44GZ44KI44GG44Gr44Gq44KM44G+44GZ44KI44GG44Gr44Kq44Ov44Op44OK44Kk44Km44K/44Oy44Km44K/44Kq44Km44Oc44Kv44Ks44Kq44Ov44OD44OG44K344Oe44Km44Oe44Ko44OL57a044Gj44Gm6YCj44Gt44Gm56eB44GM44GC44Gf44GX44GM44Gd44Gu5oCd5oOz44KS5Y+r44G244GL44KJ5o+P44GE44Gm55CG5oOz44KS44Gd44Gu5oCd44GE44Gv6Kqw44Gr44KC6Kem44KM44GV44Gb44Gq44GE6IG044GT44GI44KL44GT44Gu5aOw44GC44Gf44GX44GM44Gd44Gu6Kq56KyX44KS5o6744GN44GR44GZ44GL44KJ44KP44GL44Gj44Gm44KL5pys5b2T44Gv5ZCb44GM6Kqw44KI44KK5YSq44GX44GE44Gj44Gm44GT44Go44KS5LqM5Lq644Gv44Gp44KT44Gq44Gr44Gf44GP44GV44KT44Gu6KiA6JGJ44KS5oCd44GE44Gk44GE44Gf44GT44Go44Gg44KN44GG44G=
    ```

## Contributors
<!-- ALL-CONTRIBUTORS-LIST:START - Do not remove or modify this section -->
<!-- prettier-ignore -->
| [<img src="https://avatars2.githubusercontent.com/u/553831?v=4" width="100px;" alt="Eana Hufwe"/><br /><sub><b>Eana Hufwe</b></sub>](https://1a23.com)<br />[💻](https://github.com/COMP30022-18/NaviBee/commits?author=blueset "Code") | [<img src="https://avatars1.githubusercontent.com/u/8278409?v=4" width="100px;" alt="Zijun Chen"/><br /><sub><b>Zijun Chen</b></sub>](https://github.com/CaviarChen)<br />[💻](https://github.com/COMP30022-18/NaviBee/commits?author=CaviarChen "Code") | [<img src="https://avatars2.githubusercontent.com/u/21303543?v=4" width="100px;" alt="Wenqing Xue"/><br /><sub><b>Wenqing Xue</b></sub>](http://marsx.vip)<br />[💻](https://github.com/COMP30022-18/NaviBee/commits?author=MarsXue "Code") | [<img src="https://avatars2.githubusercontent.com/u/38567005?v=4" width="100px;" alt="Shijie Liu"/><br /><sub><b>Shijie Liu</b></sub>](https://github.com/shijiel2)<br />[💻](https://github.com/COMP30022-18/NaviBee/commits?author=shijiel2 "Code") | [<img src="https://avatars0.githubusercontent.com/u/39451885?v=4" width="100px;" alt="cmjhaha886"/><br /><sub><b>cmjhaha886</b></sub>](https://github.com/cmjhaha886)<br />[💻](https://github.com/COMP30022-18/NaviBee/commits?author=cmjhaha886 "Code") |
| :---: | :---: | :---: | :---: | :---: |
<!-- ALL-CONTRIBUTORS-LIST:END -->
