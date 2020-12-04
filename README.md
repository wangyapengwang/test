# test
项目描述
123123
git add . 
git add readme.txt
git commit -m "注释"
============================
git log 查看提交日志
git log --pretty=oneline 以一行形式查看提交日志 
git reset --hard HEAD^ 回退上一版本
所以写成HEAD~100 回退几个版本
git reset --hard 1094a 回退到指定版本
git reflog 查看命令历史
=================================
git status 查看状态1
=================================
CHENGE -1
CHENGE -2
git diff HEAD -- readme.txt 命令可以查看工作区和版本库里面最新版本的区别
命令git checkout -- readme.txt意思就是，把readme.txt文件在工作区的修改全部撤销，这里有两种情况：
一种是readme.txt自修改后还没有被放到暂存区，现在，撤销修改就回到和版本库一模一样的状态；
一种是readme.txt已经添加到暂存区后，又作了修改，现在，撤销修改就回到添加到暂存区后的状态。
总之，就是让这个文件回到最近一次git commit或git add时的状态。
用命令git reset HEAD <file>可以把暂存区的修改撤销掉（unstage），重新放回工作区
git reset命令既可以回退版本，也可以把暂存区的修改回退到工作区。当我们用HEAD时，表示最新的版本。
命令git rm删掉，并且git commit 
git checkout -b Feat_wyp 创建并切换分支
git branch 查看分支
git checkout master 切换分支到master
git merge Feat_wyp 合并到主分支
git pull 更新
git push origin master 推送到主分支
切换回自己分支进行继续开发