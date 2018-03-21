![AppIcon](https://raw.githubusercontent.com/JoaquimLey/bus-eta/master/app/src/main/res/mipmap-xxxhdpi/ic_launcher.png?token=AGmh_Pt2nHwIUsN-QmVfaL56Q0cnYrhWks5ZQYebwA%3D%3D) 

# Transport Eta
![minSdkVersion](https://img.shields.io/badge/minSdkVersion-15-green.svg?style=true)
![compileSdkVersion](https://img.shields.io/badge/compileSdkVersion-25-green.svg?style=true)


Uses SMS-Token service or the web to request a more precise ETA of a public-transport (Bus, Boat etc.) to a specific station, currently implementing for Lisbon, but the idea is to scale up to any other cities around the world that provide the same kind of service(s). Most of the complex stuff will happen on the backend anyway — yay 🙌 Cloud Functions for Firebase. Writing a well cared reactive android client codebase should be enough to adapt.

Using the new Android Architecture Components, keeping the local database as the single source of truth


### Looking for the [Medium blog post](https://medium.com/@JoaquimLey/android-architecture-components-now-with-100-more-mvvm-11629a630125) code? Check out the [blogpost branch](https://github.com/JoaquimLey/bus-eta/tree/blogpost)

## Contributing

Contributions are always welcome! Follow the "fork-and-pull" Git workflow.

**Issues:**
Fell free to open a new issue. Follow the [ISSUE_TEMPLATE.MD](../master/ISSUE_TEMPLATE.MD)

**Prevent** code-style related changes (at least run Ctrl+⌥+O, ⌥+⌘+L) before commiting.


### License

	Copyright © 2018 Joaquim Ley

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
	implied.
	See the License for the specific language governing permissions and
	limitations under the License.
