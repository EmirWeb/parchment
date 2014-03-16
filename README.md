Parchment
===============================

[![Build Status](https://travis-ci.org/EmirWeb/parchment.png?branch=master)](https://travis-ci.org/EmirWeb/parchment)

## Horizontal/Veritcal List View, GridView, ViewPager and CustomGrid GridvIew

The Parchment Library attempts to add the functionality that developers want to their AdapterViews, by providing horizontal and vertical scrolling to all of the AdapterViews. Gives developers more control of how their views layout, choose from a simple horizontal List View, a GridView that wraps heights or a GridDefinitionView that lets a user specify the grid pattern.

## Requires
Android 2.2 +

# Getting Started

## Step 1: Set up 
Set parchment up as an Android library project. Include it in your main android application. 

## Step 2: XML
Add one of the AdapterView classes (GridView, ListView, GridDefinitionView), don't forget to choose an orientation:

```xml
<com.parchment.widget.listview.ListView
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:parchment="http://schemas.android.com/apk/res/<YOUR PACKAGE NAME>"
    android:id="@+id/horizontal_list_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    parchment:isViewPager="< true | false >"
    parchment:orientation="< horizontal | vertical >"
    parchment:cellSpacing="10dp"
    parchment:isCircularScroll="< true | false >"
    parchment:snapPosition="< center | floatLeft | floatLeftWithCellSpacing | floatRight | floatRightWithCellSpacing | onScreen | onScreenWithCellSpacing>"
    parchment:snapToPosition="< true | false >" />
```

## Step 3: Java
Set Adapters and go:

```java
    mListView.setAdapter(mAdapter);
```



## Story Tracking Tool:

https://www.pivotaltracker.com/s/projects/1000984#


### License:

Copyright 2014 Emir Hasanbegovic

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
