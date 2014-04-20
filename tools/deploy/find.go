package main

import (
	"bytes"
	"fmt"
	//"os"
	"os/exec"
)

func main() {
	//cmd := exec.Command("sh", "-c", "find /home/d101270c/cbe -name \\*.log > log.txt")
	//cmd := exec.Command("sh", "-c", "find "+os.Getenv("HOME")+"/workspace -name \\*.go > go.txt")
	cmd := exec.Command("sh", "-c", "appcfg.sh --version=gcps0330a --application=sandbox4sinmetal-tg update ~/workspace/java20131116/gcp-study/src/main/webapp")
	fmt.Println(cmd)
	var out bytes.Buffer
	var stderr bytes.Buffer
	cmd.Stdout = &out
	cmd.Stderr = &stderr
	err := cmd.Run()
	if err != nil {
		fmt.Printf("stdout: %s\n", out.String())
		fmt.Printf("stderr: %s\n", stderr.String())
		fmt.Printf("%s\n", err)
		return
	}
	fmt.Printf("stdout: %s\n", out.String())
}
