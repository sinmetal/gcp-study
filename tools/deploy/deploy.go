package main

import (
	"bytes"
	"log"
	"os"
	"fmt"
	"os/exec"
	"strings"
)

func main() {
	// 環境変数設定
	os.Setenv("deploy", "/Users/shingoishimura/bin/appengine-java-sdk-1.8.9/bin/appcfg.sh")

	// 環境変数取得
	fmt.Println("deploy is", os.Getenv("deploy"))

	cmd := exec.Command(
	"appcfg.sh",
		//"deploy",
		//"/Users/shingoishimura/bin/appengine-java-sdk-1.8.9/bin/appcfg.sh",
		//"--oauth2",
		//"--version=gcps0407a",
		//"--application=sandbox4sinmetal-tg",
		//"update",
		//"~/workspace/java20131116/gcp-study/src/main/webapp")
	)

//	cmd := exec.Cmd{
//		Path: "appcfg.sh",
//		Args: []string{"appcfg.sh"},
//		Dir: "/Users/shingoishimura/bin/appengine-java-sdk-1.8.9/bin/",
//	}

//	cmd.Dir = "/Users/shingoishimura/bin/appengine-java-sdk-1.8.9/bin/"
//	cmd.Path = "appcfg.sh"
//	cmd.Args[0] = "appcfg.sh"

	cmd.Stdin = strings.NewReader("some input")
	var out bytes.Buffer
	var stderr bytes.Buffer
	cmd.Stdout = &out
	cmd.Stderr = &stderr
	err := cmd.Run()
	if err != nil {
		fmt.Printf("せつない")
		fmt.Printf("stderr: %q\n", stderr.String())
		log.Fatal(err)
	}
	fmt.Printf("stdout: %q\n", out.String())





	tests := []string{
		"hoge",
		"/bin/hoge",
		"./hoge",
		"/Users/shingoishimura/bin/appengine-java-sdk-1.8.9/bin/appcfg.sh",
		"appcfg.sh",
	}

	for _, test := range (tests) {
		if _, err := exec.LookPath(test); err != nil {
			log.Print(err)
		}
	}
}
