CUSTOM_CONFIG=./.customConfig
CUSTOM_TOKEN=./.customToken
SAVED_CONFIG=./.savedConfig
SAVED_TOKEN=./.savedToken
LOCAL_CONFIG=$HOME/.gitconfig
LOCAL_TOKEN=$HOME/.git-credentials

if [ -f "$LOCAL_CONFIG" ]; then
    cp $LOCAL_CONFIG $SAVED_CONFIG
fi

if [ -f "$LOCAL_TOKEN" ]; then
    cp $LOCAL_TOKEN $SAVED_TOKEN
fi

cp $CUSTOM_CONFIG $LOCAL_CONFIG
cp $CUSTOM_TOKEN $LOCAL_TOKEN

echo "Logged in"
read -p "Press Enter to log out..."

if [ -f "$SAVED_CONFIG" ]; then
    cp $SAVED_CONFIG $LOCAL_CONFIG
    rm $SAVED_CONFIG
else
    rm $LOCAL_CONFIG
fi

if [ -f "$SAVED_TOKEN" ]; then
    cp $SAVED_TOKEN $LOCAL_TOKEN
    rm $SAVED_TOKEN
else
    rm $LOCAL_TOKEN
fi


unset CUSTOM_CONFIG
unset CUSTOM_TOKEN
unset SAVED_CONFIG
unset SAVED_TOKEN
unset LOCAL_CONFIG
unset LOCAL_TOKEN
