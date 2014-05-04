package main

import (
	//"bytes"
	"log"
	"io"
	"os"
	"fmt"
	"os/exec"
	//"strings"
)

func main() {
	// 環境変数設定
	os.Setenv("deploy", "/Users/shingoishimura/bin/appengine-java-sdk-1.8.9/bin/appcfg.sh")

	// 環境変数取得
	fmt.Println("deploy is", os.Getenv("deploy"))

	cmd := exec.Command(
		//"appcfg.sh", "--version=gcps0426a", "--application=sandbox4sinmetal-tg","update" ,"~/workspace/java20131116/gcp-study/src/main/webapp",
		"appcfg.sh", "--oauth2", "--version=gcps0426a", "--application=sandbox4sinmetal-tg", "update" ,"/Users/shingoishimura/workspace/java20131116/gcp-study/src/main/webapp",
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

	stdout, err := cmd.StdoutPipe()
	if err != nil {
		log.Fatal(err)
	}
	fmt.Printf("cmd.Start start!!")
	if err := cmd.Start(); err != nil {
		log.Fatal(err)
	}
	fmt.Printf("cmd.Start end!!")

	go io.Copy(os.Stdout, stdout)

	if err := cmd.Wait(); err != nil {
		log.Fatal(err)
	}

//	fmt.Printf("cmd.Wait start!!")
//	if err := cmd.Wait(); err != nil {
//		log.Fatal(err)
//	}
//	fmt.Printf("cmd.Wait end!!")
//
//	buf := new(bytes.Buffer)
//	buf.ReadFrom(stdout)
//	fmt.Printf("%s", buf.String())
//
//	cmd.Stdin = strings.NewReader("some input")
//	var out bytes.Buffer
//	var stderr bytes.Buffer
//	cmd.Stdout = &out
//	cmd.Stderr = &stderr
//	//err := cmd.Run()
//	if err != nil {
//		fmt.Printf("stdout: %s\n", out.String())
//		fmt.Printf("stderr: %s\n", stderr.String())
//		log.Fatal(err)
//	}
//	fmt.Printf("stdout: %s\n", out.String())

//	go func(in io.WriteCloser) {
//		for i := 0; i < 5; i++ {
//			in.Write([]byte(fmt.Sprintf("hoge_%d", i)))
//			time.Sleep(1 * time.Second)
//		}
//	}(stdin)



//	tests := []string{
//		"hoge",
//		"/bin/hoge",
//		"./hoge",
//		"/Users/shingoishimura/bin/appengine-java-sdk-1.8.9/bin/appcfg.sh",
//		"appcfg.sh",
//	}
//
//	for _, test := range (tests) {
//		if _, err := exec.LookPath(test); err != nil {
//			log.Print(err)
//		}
//	}
}
