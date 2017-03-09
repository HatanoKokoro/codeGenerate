# codeGenerate
代码自动生成jar包源码<br>
###包含的方法
####CreateFile(tableName, createUser, packageUrl, prefix)
tableName为表名,createUser为创建者,packageUrl为创建根目录(一般为com),prefix为主键前缀如输入wy则生成表insert时主键为wyXXXX000001
####CreateController(tableName,className, list, annotation, map, pk)
####CreateEntity(className, list, annotation, map)
####CreateIService(className, list, annotation, map, pk)
####CreateService(className, list, annotation, map, pk)
####CreateIDao(className, list, annotation, map, pk)
####CreateSqlMapper(tableName, className, list, annotation, map, pk)
####CreateListJSP(className, list, annotation, map, pk)
####CreateAddJSP(tableName,className, list, annotation, map, pk)
####CreateEditJSP(tableName, className, list, annotation, map, pk)
####CreateDetailJSP(tableName, className, list, annotation, map, pk)
tableName 为表名,className为驼峰格式表名,list中存储表字段信息,annotation是一个Annotation类,其中存储作者信息,map中存储从xml文件中获取的信息以及初始化信息,pk为主键
