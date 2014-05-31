package main

import (
	"fmt"
	"log"
	"os"
	"os/exec"
	"time"
)

func main() {
	// 環境変数設定
	os.Setenv("deploy", "/Users/shingoishimura/bin/appengine-java-sdk-1.8.9/bin/appcfg.sh")

	// 環境変数取得
	fmt.Println("deploy is", os.Getenv("deploy"))

	defaultVersion := time.Now().Format("200601021504")
	fmt.Println("please version")
	fmt.Println("default version = " + defaultVersion)

	var version string
	_, err := fmt.Scanln(&version)
	if err != nil {
		if err.Error() == "unexpected newline" {
			version = defaultVersion
		} else {
			log.Fatal(err)
		}
	}
	version = "--version=" + version

	cmd := exec.Command(
		"appcfg.sh", version, "--application=map-sample-201300606", "--passin", "--no_cookies", "update", "/Users/shingoishimura/workspace/java20131116/gcp-study/src/main/webapp",
	)
	fmt.Println(cmd.Args)

	cmd.Stdin = os.Stdin
	cmd.Stdout = os.Stdout
	cmd.Stderr = os.Stderr

	if err := cmd.Start(); err != nil {
		log.Fatal(err)
	}

	if err := cmd.Wait(); err != nil {
		log.Fatal(err)
	}
}
