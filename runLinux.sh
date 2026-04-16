LOGIN=./executables/logInLinux.sh
REGISTER=./executables/registerLinux.sh
CUSTOM_CONFIG_FILE=./saves/.customConfig
CUSTOM_TOKEN_FILE=./saves/.customToken


callerDirectory=$PWD
cd "$(dirname "${BASH_SOURCE[0]}")"

if ! git --version &> /dev/null; then
    echo "Git is not available!"
    exit 1
fi
    
if ! [[ -d "./saves" ]]; then
    mkdir "./saves"
fi

while [ true ]; do
    if [[ -f "$CUSTOM_CONFIG_FILE" && -f "$CUSTOM_TOKEN_FILE" ]]; then
        echo "Select an action: "
        echo "1) Reregister"
        echo "2) Log In"
        echo "3) Exit"
        read selection
        if [[ "$selection" == "1" ]]; then
            . "$REGISTER"
        elif [[ "$selection" == "2" ]]; then
            . "$LOGIN"
        elif [[ "$selection" == "3" ]]; then
            break
        else
            printf "Invalid action: %s (expected 1, 2 or 3)" "$selection"
        fi
        unset selection
    else
        . "$REGISTER"
    fi
done

cd "$callerDirectory"

unset callerDirectory
unset LOGIN
unset REGISTER
unset CUSTOM_CONFIG_FILE
unset CUSTOM_TOKEN_FILE