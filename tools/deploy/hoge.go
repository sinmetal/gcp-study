package main

import (
	"bytes"
	"os/exec"
	"fmt"
)

func main() {
	app := "momo.sh"
	//app := "ls"
	//app := "buah"

	arg0 := "-e"
	arg1 := "Hello world"
	arg2 := "\n\tfrom"
	arg3 := "golang"

	cmd := exec.Command(app,arg0,arg1,arg2,arg3)
	var stdout bytes.Buffer
	var stderr bytes.Buffer
	cmd.Stdout = &stdout
	cmd.Stderr = &stderr
	//out, err := cmd.Output()

	if err != nil {
		fmt.Printf("stdout: %s\n", stdout.String())
		fmt.Printf("stderr: %s\n", stderr.String())
		println(err.Error())
		return
	}

	print(string(out))
}
