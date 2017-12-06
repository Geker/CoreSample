## 说明 ##


**调用脚本前，请先配置好当前目录下的batch.conf**

1. batchBak.sh
    按照特定的格式备份zk数据到文件并压缩。
2. batchBakInsert2DB.sh
    读取上一步batchBak压缩的文件内容，按照配置的mysql地址将内容插入数据库的对应表。
3. batchDel.sh
	- 能够删除执行记录，同样依赖batch.conf的zk配置和batchGrp配置。
    - 删除Servers、TaskExecutions目录下对应的执行记录。
    **最好确保删除之前已经调用batchBak进行了合理的备份。**
4. batchCheck.sh
	检查特定grp下面的所有执行记录。如果有异常，会有文件输出。依赖zk配置和batchGrp配置。
    - 输出异常文件列表之后，会删除ExecutingTasks目录下的记录。(此目录记录非正常完成状态的任务信息，不应包含太多记录，需要*检查*为何失败)
5. 调用los服务
	A.修改batch.conf里面的zk地址和svc.name；
	B.增加参数列表在batch.conf里面
	C.执行invoke.sh

