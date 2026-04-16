CUSTOM_CONFIG=../saves/.customConfig
CUSTOM_TOKEN=../saves/.customToken


cd executables

read -rp "Enter your git username: " username
read -rp "Enter your git mail: " mail
read -rp "Enter your login token: " token

touch "$CUSTOM_CONFIG"
if [[ -f "$CUSTOM_TOKEN" ]]; then
    rm "$CUSTOM_TOKEN"
    touch "$CUSTOM_TOKEN"
fi

git config --file "$CUSTOM_CONFIG" user.name "$username"
git config --file "$CUSTOM_CONFIG" user.mail "$mail"
git config --file "$CUSTOM_CONFIG" credential.helper store
printf https://%s:%s@github.com "$username" "$token" > "$CUSTOM_TOKEN"

cd ..

unset CUSTOM_CONFIG
unset CUSTOM_TOKEN
unset username
unset mail
unset token



