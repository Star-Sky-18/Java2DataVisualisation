# CS209 Assignment2

## Data visualisation

Last time, we preprocess the files from your friend, and collect them into a systemically classified, 
and consistent encoded situation. Then, you want to know some general information of these files. 
However, your another friend also become interested in these files. He wants you to give him these information  visually.


To complete this, you need to write a JavaFX program for data visualisation.

*summary* : 
1. Load a default config file to get the 'root' and the 'intervals'
2. Draw the bar chart for number of files per genre
3. Draw the charts for different file size intervals of each genre
4. User can change the 'root' and the 'intervals' by button or other interaction
5. (Optional) User can change the chart type for 'intervals' charts by choice box or other interaction

### 1. Load a default config file
The default config file is named config.yml and put in the root of the project,
which means the path is "./config.yml".

A possible configuration file: 
```$yml
Root: ./test_datas
Intervals:
  - 100
  - 2000
  - 4000
IntervalNames:
  - small
  - mid
  - big
  - giant
```
The basic rules for configuration are as follows:

 - Root means the directory root for Counter
 - Intervals mean the size interval for classification
 , in this example, it means [0,100), [100,2000),[2000,4000),[4000,+) bytes.
 - IntervalNames means the name for each interval.
 - The length of IntervalNames will be always one more than the intervals'  

 The encoding of this file will be always UTF-8.

 ### 2.Draw the first chart (Genre Statistics)
 For this example, the file tree is shown as following:
 ```
test_datas
    │  newout.csv
    │  output_for_test_case_1
    │
    ├─专题报道
    │  ├─南科大
    │  │  └─半年以上
    │  │          sustech_0_cs.txt
    │  │          sustech_13_cs.txt
    │  │          sustech_1_cs.txt
    │  │          sustech_4_cs.txt
    │  │          sustech_7_cs.txt
    │  │
    │  ├─文匯報
    │  │  └─一月内
    │  │          sustech_16_cs.txt
    │  │          sustech_17_cs.txt
    │  │
    │  └─深圳本地宝
    │      └─半年以上
    │              sustech_15_cs.txt
    │
    ├─书院新闻
    │  └─南科大
    │      └─半年以上
    │              sustech_11_cs.txt
    │              sustech_6_cs.txt
    │              sustech_9_cs.txt
    │
    ├─人文
    │  ├─人日
    │  │  └─一月内
    │  │          hi_5_there.txt
    │  │
    │  ├─天涯
    │  │  └─半年以上
    │  │          hi_10_there.txt
    │  │
    │  └─腾讯
    │      └─半年以上
    │              hi_15_there.txt
    │
    ├─体育
    │  ├─人日
    │  │  └─半年内
    │  │          hi_13_there.txt
    │  │
    │  ├─新浪
    │  │  └─一周内
    │  │          hi_8_there.txt
    │  │
    │  └─腾讯
    │      └─半年内
    │              hi_3_there.txt
    │
    ├─教学新闻
    │  └─南科大
    │      ├─半年以上
    │      │      sustech_14_cs.txt
    │      │      sustech_3_cs.txt
    │      │
    │      └─半年内
    │              sustech_12_cs.txt
    │
    ├─时政
    │  ├─人日
    │  │  └─一周内
    │  │          hi_1_there.txt
    │  │
    │  ├─天涯
    │  │  └─一月内
    │  │          hi_6_there.txt
    │  │
    │  └─腾讯
    │      └─半年内
    │              hi_11_there.txt
    │
    ├─生活
    │  ├─天涯
    │  │  └─一周内
    │  │          hi_2_there.txt
    │  │
    │  ├─新浪
    │  │  └─半年内
    │  │          hi_12_there.txt
    │  │
    │  └─腾讯
    │      └─半年内
    │              hi_7_there.txt
    │
    ├─科技
    │  ├─人日
    │  │  └─半年内
    │  │          hi_9_there.txt
    │  │
    │  ├─天涯
    │  │  └─半年以上
    │  │          hi_14_there.txt
    │  │
    │  └─新浪
    │      └─一月内
    │              hi_4_there.txt
    │
    ├─科研新闻
    │  └─南科大
    │      └─半年以上
    │              sustech_10_cs.txt
    │              sustech_5_cs.txt
    │
    └─综合新闻
        └─南科大
            └─一月内
                    sustech_2_cs.txt
                    sustech_8_cs.txt
 ```
- you can ignore the files in root.
- you should categorize according to the first level catalog.
- this chart should be a bar chart.

The statistical logic has been provided as Counter.java.
And you should read and use it.

One possible result is shown following:
![image-20200326141955865](pictures\image-20200326141955865.png)

### 3.Draw the second charts (File size statistics)
- You should draw a pie chart for each genre, which means in this example there will be 10 pie charts. 
- Obviously, the number of charts is dynamically
 determined by the root, so you should dynamically add the charts to pane 
 (or other reasonable ways) by runtime.
- for each pie chart:

    - the name of each part should be the string in the corresponding intervalNames.
    - the value of each part should be the ratio of the number of current interval files to the total.
    - if the ratio is 0, which means there no file is in this interval, then this interval should not be show.
    - the title of this pie chart should be the corresponding name in first chart. 
    

The statistical logic has been provided as Counter.java.
And you should read and use it.

One possible result is shown following:

![image-20200326143724960](pictures\image-20200326143724960.png)

![image-20200326143825234](pictures\image-20200326143825234.png)

### 4.Change the root and intervals settings by interaction
1. Change the root setting:

    Use a DirectoryChooser to choose the new root, and then statistics it (know that the intervals has not been changed) and refresh your UI.

2. Change the intervals setting:

    Use a FileChooser to choose a new yml file, which file will contain the 'Intervals' and 'IntervalNames' 
    (or more information, but you only pay attention to these two), and then statistics by this new setting (know that the root has not been changed) 
    and refresh your UI.

3. Change both two settings:

    Use a FileChooser to choose a new yml file, which file should have the information as default config file have, 
    and then statistics it and refresh your UI.

**The FileChooser should just accept \*.yml files**

These interactions can be trigger by buttons or other modules.

### (Optional) 5.change the chart type for 'intervals' charts by interaction
User can change the chart type within (pie chart, stacked bar chart) or more.

Following is a possible stacked bar chart:

![image-20200326150520501](pictures\image-20200326150520501.png)

### Other 
- When some error was caused by the user, you can alert the user.
  
    For example: user choose a yml file which does not contain the info we need,then you can alert that the yml file is wrong.
- Any reasonable interactions is acceptable