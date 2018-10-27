#!/bin/bash

FILEPATH=$1

PROJECT_ROOT_DIR=$4
VERSION_FILE="${PROJECT_ROOT_DIR}/generation_version.properties"
#检测App是否有更新
#echo $(curl -S "https://www.pgyer.com/apiv2/app/check" -F "buildVersion=${VERSION_NAME}" -F "_api_key=${API_KEY}" -F "appKey=${APP_KEY}")
#获取App所有版本
#echo $(curl -S "https://www.pgyer.com/apiv2/app/builds"  -F "_api_key=${API_KEY}" -F "appKey=${APP_KEY}")
#获取App详细信息
APP_DETAIL_URL=https://www.pgyer.com/apiv2/app/view
API_KEY=$2
APP_KEY=$3


function main(){

    checkVersion ${VERSION_FILE}
    upload
}

function checkVersion(){
    echo -e "\033[34m ======================checkVersion start ====================== \033[0m"
    if [ ! -f "$1" ]; then
        echo "no exit $1"
        exit 1
    fi
    while read line
    do
         versionName=\"${line}\"
        echo "local version :$versionName"
    done < $1


    result0=$(curl -S "$APP_DETAIL_URL"  -F "_api_key=${API_KEY}" -F "appKey=${APP_KEY}")
    data=$(echo ${result0} | awk -F ':' '{print $13}'  | awk -F ',' '{print $1}')
    echo "remote version $data"


    if [ "$data" == "$versionName" ]; then
        echo "no need update,current version is $versionName"
        echo -e "\033[34m ======================checkVersion end  ====================== \033[0m"
        exit 0
    fi
    echo -e "\033[34m ======================checkVersion end  ====================== \033[0m"

}
function panic()
{
    local exitCode=$1
    set +e

    shift
    [[ "$@" == "" ]] || \
        echo "$@" >&2

    exit $exitCode
}

function upload(){
    echo "updating,new version is $versionName"
    echo -e "\033[34m ======================upload start  ====================== \033[0m"

    if [ ! -n "$FILEPATH" -o ! -n "$API_KEY" ]; then
	panic 1 "Usage: ./pgyer_upload.sh <FilePath> <APIKey>"
    fi

    if [ ! -f "$FILEPATH" ]; then
        panic 1 "PGYER: can not find file, pelase check yout file path"
    fi

    result=$(curl -S "http://www.pgyer.com/apiv2/app/upload" -F "file=@${FILEPATH}" -F "_api_key=${API_KEY}")

    code=$(echo $result | awk -F ':' '{print $2}' | awk -F ',' '{print $1}')
    message=$(echo $result | awk -F ':' '{print $3}' | awk -F '"' '{print $2}')

    if [ $code -eq 0 ]; then
        panic 0 "PGYER: upload app package to PGYER success"
    else
        panic 1 "PGYER: upload app package to PGYER failed! Error message: ${message}"
    fi

    echo -e "\033[34m ======================upload end  ====================== \033[0m"

}



main
