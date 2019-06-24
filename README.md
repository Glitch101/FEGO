# FEGO
## What is it
FEGO for ***Feel Good** is a gadget powered by **Spotify** that allows you to control your music by buttons. It uses Spotify's API to do a lot of things. Some are basic and others are cool.<br>
**You don't need to open Spotify to do any of the things written below.**
### Features
<details><summary>What can FEGO do?</summary>
  1.Play,pause,resume music.<br>
  2.Go to next/previous song or repeat current song.<br>
  3.Play any two favourite playlists by the press of a single button. You can change the two playlists anytime through the Android app.<br>
  4.Add the current playing song to your Spotify library.<br>
  5.Blacklist a song. You will never hear it again!<br>
  6.Switch the audio playback from your computer on which Spotify is open to your mobile by the press of a single button.<br>
  7. Party mode. LED lights built into FEGO respond according to your music.<br>
  </details>
  
## What's it made of?
FEGO has two main components. It has an Android app named FEGO from which you can change your two favourite playlists by changing the uri in the app. The uri can be copied from spotify app by Playlist>Share>Copy link.<br>Note that link and uri are not the same thing. See the example below:
> Spotify Playlist link : https://open.spotify.com/user/spotify/playlist/37i9dQZF1DWY8U6Zq7nvbE?si=oWZprEa9Scu9DjFs3opEQA<br>
> Spotify Playlist uri : spotify:user:spotify:playlist:37i9dQZF1DWY8U6Zq7nvbE<br>

You cannot get playlist uri fron Spotify app. Only playlist link can be copied. But it is possible from Spotify desktop app.<br>
Don't worry though. **FEGO app can automatically extract the uri even if you enter link into it.**<br>

The android app also lets you remove a song from blacklist or add another one into it by giving it  the uri of that track(need to get that from Spotify app), although a song can also be added to blacklist from FEGO's push button(only the current playing track).<br>

The other component of FEGO is a hardware interface consisting of a Raspberry Pi 3B+, an Arduino Uno, LED's and lots of push buttons(each serves a different function as specified above).<br>
The FEGO talks to it's android app and the android app talks to Spotify. I guess that's what you call IOT! Lots of devices connected to the internet, talking to each other.<br>

## How it works!
