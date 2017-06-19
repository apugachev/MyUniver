## Android app on My Univer API

Through my university program, I have created an Android app working with [My Univer API](http://studyx.co/en/api/start/). For the realization of this goal I studied Java, work with My Univer API and work with GET / POST HTTP requests. This project was made under the leadership of Skolkovo Innovation Center's employee.

## Idea

The idea of my app is the following. Each student of the group in the university or each pupil of the class in the school shares with his group or class mates where would he go the next day. This information gathers on the server and each student can see where would all his group or class mates go the next day.

## Structure of any Android app

Each Android app consists of "windows" which are called `Activity`. Usually in a particular moment of time there appears only one `Activity` which occupies the whole screen and the app switches between `Activities`. One app can have many `Activities`.  The content of `Activity`  is formed by different components called `View`. These can be a `Button`, `TextView`, `RadioButton` and anything else. `Activity` files have filename extension `.java`.

Files with extension `.xml` are made for the description of elements in `Activity`. They consist information about all of the elements that are situated in particular `Activity`, also markup and `layout`. Also there are `.xml` files which consist information about the name of the app, fonts, colors and styles.

## Detailed description of my app

The first version of my app had only 2 `Activities`. Subsequently I decided to improve the functionality of my app and leaned how to work with `Navigation Drawer` and `Fragment`. The `Navigation Drawer` is a template which has a unique feature - a sidebar called `Drawer`.  The `Drawer` appears from the left side and has some menu items. My app has 3 menu items: **Main**, **List** and **About**.

In the **Main** section there is `fragment_main` `Fragment` with the form for writing. In this `Fragment` there are two `TextView`, input field for name `EditText` and a `RadioGroup` with three `RadioButton`. Depending on where the student will go, the appropriate `RadioButton` activates. User types information in `EditText ` and presses **Save** button. By tapping this button there appears `Toast` message. The written information is being saved on the server.

In the **List** section there is `fragment_list` `Fragment` with two `TextView` which show students and places where they go. This information is taken from the server and is displayed in `TextView`.

In the **About** section there is `fragment_about` `Fragment`  with the information about the developer and his mentor. There are also two `Button` in order to communicate with developer.

## Realization of server requests

For the server requests there were used methods of [OkHttp client](http://square.github.io/okhttp/) for Android and Java.

### Writing to the server

Writing to the server is performed as follows. There is formed a string with the data which must be written. 

For example:

````
http://dev.moyuniver.ru/api/php/v03/_places/api_set_place.php?memberid=28665485147fa7133b44cb&appid=306&appsgn=d8629af695839ba5481757a519e57fb1&appcode=&os=&ver=&width=&height=&place=home
````

where:

`memberid=28665485147fa7133b44c`  - student's ID

`place=home` - place where the student goes

For the formation of request there was created a template:

````
String finalurl;
private String mainurl = "http://dev.moyuniver.ru/api/php/v03/_places/api_set_place.php?memberid=";
private String addurl = "&appid=306&appsgn=d8629af695839ba5481757a519e57fb1&
appcode=&os=&ver=&width=&height=&place=";
````

Student types his name and the place where he goes. This data is being saved in `stringName` and `stringWhere`. Then by tapping the `Save` button the method which sends the information to the server is being run. The realization is the following:

````java
View.OnClickListener btnclick = new View.OnClickListener() {
    private OkHttpClient OkHttpClient;
    private Request request;

    public  void onClick(View v) {
        final String stringName = nameText.getText().toString();
        String stringWhere = edittextWhere.getText().toString();
        finalurl = mainurl + stringName + addurl + stringWhere; // make a string - request
        edittextWhere.setText(""); // clean the fields
        nameText.setText("");
        Toast toast = Toast.makeText(getActivity().getApplicationContext(), // show Toast
        "Записано!", Toast.LENGTH_SHORT);
        toast.show();
    
        OkHttpClient = new OkHttpClient();
        request = new Request.Builder().url(finalurl).build(); // send request
        OkHttpClient.newCall(request).enqueue(new Callback() {

        @Override
        public void onFailure(Call call, IOException e) {}

        @Override
        public void onResponse(Call call, Response response) throws IOException {}
        });
        
    }
};
````

In the end the string - request `finalurl` is formed and the information is sent to the server.

### Reading from the server

There is a string with template `<memberid>#<place>` on the server on the next request:

````
http://dev.moyuniver.ru/api/php/v03/_places/api_get_places.php?memberid=28665485147fa7133b44cb&appid=306&appsgn=d8629af695839ba5481757a519e57fb1&appcode=&os=&ver=&width=&height=
````

where:

`memberid` - student's ID

`place` - place where the student goes.

The string that is saved on the server is being parsed and each substring (ID or place) is being displayed in the appropriate `TextView`. For this was written the following method:

````java
private String url = "http://dev.moyuniver.ru/api/php/v03/_places/api_get_places.php?
memberid=28665485147fa7133b44cb&appid=306&appsgn=d8629af695839ba5481757a519e57fb1&
appcode=&os=&ver=&width=&height=";

...

OkHttpClient = new OkHttpClient();
request = new Request.Builder().url(url).build();

OkHttpClient.newCall(request).enqueue(new Callback() {
    @Override
    public void onFailure(Call call, IOException e) {
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        mainstring = response.body().string(); // save the string from the server
        int i = 0; // launch our parser
        while (i < mainstring.length()) {
            if (mainstring.charAt(i) != '#' && 	mainstring.charAt(i) != '\n') {
                part = part + mainstring.charAt(i);
                ++i;
            } else if (mainstring.charAt(i) == '#') {
                part = part.replace("null", "");
                final String FirstPart = part; // save ID
                Handler handler = new Handler(getActivity().getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        name = (TextView) getActivity().findViewById(R.id.name);
                        String text = name.getText().toString();
                        String newtext = text + "\n" + FirstPart;
                        name.setText(newtext); // write ID to TextView
                    }
                } );
                part = "";
                ++i;
                } else if (mainstring.charAt(i) == '\n') {
                    ++i;
                    final String SecondPart = part; // save place
                    Handler handler = new Handler(getActivity().getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            place = (TextView) getActivity().findViewById(R.id.place);
                            String text = place.getText().toString();
                            String newtext = text + "\n" + SecondPart;
                            place.setText(newtext); // write place to TextView
                        }
                    } );
                    part = "";
                }
            }
    }
});
````

That is how data from server is read and displayed in `TextView`.

## Tools

The whole code was written in [IDE Android Studio 2.3](https://developer.android.com/studio/index.html). The minimum version of API of the app - API 21, Android 5.0 Lollipop. According to [Google's Dashboard](https://developer.android.com/about/dashboards/index.html) more than 70% of Android devices run on Android 5.0 or newer nowadays. My app was tested on the following devices:



Samsung Galaxy S7, Android 7.0 Noughat, resolution 2560 x 1440

HTC One M7, Android 5.0 Lollipop, resolution 1920 x 1080

Xiaomi Redmi Note 3 Pro, Android 6.0.1 Marshmallow, resolution 1920 x 1080

## Source

[Google's documentation](https://developer.android.com/develop/index.html)

[My Univer API](http://studyx.co/en/api/start/)

[Fandroid](http://www.fandroid.info/)

[OkHttp client](http://square.github.io/okhttp/)

## Download

My app can be downloaded from [here](https://github.com/apugachev/MyUniver/blob/master/myuniver.apk).

## Screenshots

![pic](D:\pic.PNG)

