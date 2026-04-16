SAVED_CONFIG=../saves/.savedConfig
SAVED_TOKEN=../saves/.savedToken


cd executables

read -rp "Enter your git username: " username
read -rp "Enter your git mail: " mail
read -rp "Enter your login token: " token

touch "$SAVED_CONFIG"
if [[ -f "$SAVED_TOKEN" ]]; then
    rm "$SAVED_TOKEN"
    touch "$SAVED_TOKEN"
fi

git config --file "$SAVED_CONFIG" user.name "$username"
git config --file "$SAVED_CONFIG" user.mail "$mail"
git config --file "$SAVED_CONFIG" credential.helper store
printf https://%s:%s@github.com "$username" "$token" > "$SAVED_CONFIG"

cd ..

unset SAVED_CONFIG
unset SAVED_TOKEN
unset username
unset mail
unset token



