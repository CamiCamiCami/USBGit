LOGIN=./executables/logInLinux.sh
REGISTER=./executables/registerLinux.sh


callerDirectory=$PWD
cd "$(dirname "${BASH_SOURCE[0]}")"

if ! [[ -d "./saves" ]]; then
    mkdir "./saves"
fi

while [ condition ]
do
    if [[ -f "$SAVED_CONFIG" && -f "$SAVED_TOKEN" ]]; then
        echo "Select an action: "
        echo "1) Reregister"
        echo "2) Log In"
        read selection
        if [[ "$selection" == "1" ]]; then
            . "$REGISTER"
        elif [[ "$selection" == "2" ]]; then
            . "$LOGIN"
        else
            printf "Invalid action: %s (expected 1 or 2)" "$selection"
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