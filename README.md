# StayOn

A Tiny app for developers who are dissatisfied with 
'Settings > Developer Options > Stay awake when charging' option.
This option keep screen on while just charging.


### But, this app will keep screen on while just PC-USB is attached.

- This app will listen ACTION_POWER_CONNECTED and DISCONNECTED
- Use wake lock when pc use is attached.
- It may take some time to get event. in my case, it take about 3minutes (Nexus 5x)
- If your phone can not distinguish AC connected and USB connected, this app may not work properly.
