/*
 * Copyright (c) Sree Harsha Mamilla.
 */

Lobesplit is a fun android music player which lets you the user
listen to two different audio files simultaneously in each earphone.

The application list out all the music file and lets user to select
song to be played in selected stream (left or right earphone).


The idea is to have two MediaPlayer objects and then assign two
songs to each mediaplayer object. Then use setVolume() function
to do the magic of letting songs play in selected stream.
