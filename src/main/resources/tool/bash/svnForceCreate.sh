#!/bin/bash

if [ $# -lt 4 ]
  then
    echo "Example: ./forceCreateTagFromBranch.sh <SVN_UID> <SVN_PASS> <FROM_BRANCH_NAME> <TARGET_TAG_NAME>"
    exit -1
fi

MODULES=(sub_module_1, sub_module_2, sub_module_3, sub_module_4)

for MODULE_NAME in ${MODULES[@]}
do
        svn --username $1 --password $2 info https://dummy.svn.url/$MODULE_NAME/$4
        if [ $? -eq 0 ]
        then
                echo "Module $MODULE_NAME already exist, overriding..."
                svn --username $1 --password $2 remove https://dummy.svn.url/$MODULE_NAME/$4 -m "Deleting $4"
                svn --username $1 --password $2 copy   https://dummy.svn.url/$MODULE_NAME/$3 https://dummy.svn.url/$MODULE_NAME/$4 -m "Created $4"
                if [ $? -eq 0 ]
                then
                        echo "Module $MODULE_NAME successfully overrided."
                fi
        else
                echo "Module $MODULE_NAME doesn't exist, copying..."
                svn --username $1 --password $2 copy   https://dummy.svn.url/$MODULE_NAME/$3 https://dummy.svn.url/$MODULE_NAME/$4 -m "Created $4"
                if [ $? -eq 0 ]
                then
                        echo "Module $MODULE_NAME successfully created."
                fi
        fi
done