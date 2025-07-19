<p align="center"><h1 align="center">ROZSDB-LITE</h1></p>
<p align="center">
        <img src="https://jitpack.io/v/Weesli/RozsDB-Lite.svg" onclick="window.location.href = 'https://jitpack.io/#Weesli/RozsDB-Lite';" alt="RozsDB-Lite">
</p>
<p align="center">
	<!-- default option, no dependency badges. -->
</p>
<br>

##  Overview

RozsDBLite, unlike RozsDB, is local-file-based and designed for use with more embedded projects. SpigotAPI and local applications are examples of this.

---

##  Features

- Key=Value-based record system (JSON)
- Extremely light and suitable for use with API
- Fast and easy to use
- Free and open source
- No dependencies
- Built using performance-based technologies such as DSL-JSON and ZSTD!


---
##  Getting Started

How to use the API can be found in the [wiki](https://github.com/Weesli/RozsDB-Lite/wiki).

###  API

Gradle Groovy:

```groovy

repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}
```
```groovy
dependencies {
    implementation 'com.github.Weesli:RozsDB-Lite:<version>'
}
```

Gradle Kotlin:

```kotlin

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}
```
```kotlin
dependencies {
    implementation("com.github.Weesli:RozsDB-Lite:<version>")
}
```

Maven:

```xml

<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
```xml
<dependency>
    <groupId>com.github.Weesli</groupId>
    <artifactId>RozsDB-Lite</artifactId>
    <version>{version}</version>
</dependency>
```


---

##  Contributing

- **💬 [Join the Discussions](https://github.com/Weesli/RozsDB-Lite/discussions)**: Share your insights, provide feedback, or ask questions.
- **🐛 [Report Issues](https://github.com/Weesli/RozsDB-Lite/issues)**: Submit bugs found or log feature requests for the `RozsDB-Lite` project.
- **💡 [Submit Pull Requests](https://github.com/Weesli/RozsDB-Lite/blob/main/CONTRIBUTING.md)**: Review open PRs, and submit your own PRs.

<details closed>
<summary>Contributing Guidelines</summary>

1. **Fork the Repository**: Start by forking the project repository to your github account.
2. **Clone Locally**: Clone the forked repository to your local machine using a git client.
   ```sh
   git clone https://github.com/Weesli/RozsDB-Lite
   ```
3. **Create a New Branch**: Always work on a new branch, giving it a descriptive name.
   ```sh
   git checkout -b new-feature-x
   ```
4. **Make Your Changes**: Develop and test your changes locally.
5. **Commit Your Changes**: Commit with a clear message describing your updates.
   ```sh
   git commit -m 'Implemented new feature x.'
   ```
6. **Push to github**: Push the changes to your forked repository.
   ```sh
   git push origin new-feature-x
   ```
7. **Submit a Pull Request**: Create a PR against the original project repository. Clearly describe the changes and their motivations.
8. **Review**: Once your PR is reviewed and approved, it will be merged into the main branch. Congratulations on your contribution!
</details>

<details closed>
<summary>Contributor Graph</summary>
<br>
<p align="left">
   <a href="https://github.com{/Weesli/RozsDB-Lite/}graphs/contributors">
      <img src="https://contrib.rocks/image?repo=Weesli/RozsDB-Lite">
   </a>
</p>
</details>

---

##  License

```md
MIT License

Copyright (c) 2025 Weesli

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
---