# StayOn

A Tiny app for developers who are dissatisfied with 
'Settings > Developer Options > Stay awake when charging' option.
This option keep screen on while just charging.


### But, this app will keep screen on while just PC-USB is attached.

- This app will listen ACTION_POWER_CONNECTED and DISCONNECTED
- Acquire wake lock while pc use is attached, and release wake lock when detached.
- It may take some time to listen event. in my case, Nexus 5x takes about 3 minutes, but Galaxy S4 take few seconds.
- If your phone can not distinguish AC connected and USB connected, this app may not work properly.


### Screen Shots
![screenshot](https://lh3.googleusercontent.com/Xe6CVGcdGPaTo1ltrXM_8xo9ZADpxb63g9rFRK4a5pVU1m1XUoziNMXKL3w-KlRIFQ=h310-rw)
![screenshot](https://lh3.googleusercontent.com/9-xLQbpE3sFxFRMZOhT4tvXvpEYhRUHKZFHSTKDrrPki3KjMnK8-11L5HYLvPdJWhvY=h310-rw)


### Play Store
- https://play.google.com/store/apps/details?id=kein.stayon
