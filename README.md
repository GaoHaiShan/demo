demo 简介
	mySimTomat 项目 
		netty 实现简单 tomcat demo
		nio 实现简单 tomcat demo
		（直接启动访问 127.0.0.1:8080/first.do 或者 127.0.0.1:8080/seco.do即可）
	其余三个文件是实现分布式远程调用 demo
		netty-rpc-client（客户端）    register(注册中心)  rpc-netty-service（服务端）
		部署：
			模块 netty-rpc-client（客户端）    register(注册中心)  rpc-netty-service（服务端）
			将 rpc-netty-service 里 api模块打成 jar 包 并且引入 netty-rpc-client 和 register中 即可完成部署
		启动顺序
			先启动 register
			在启动 rpc-netty-service
			最后启动 netty-rpc-client

学习内容
tomcat 简单流程
1、初始化 xml 文件 servlet 初始化
2、建立底层通信
3、获取用户想要访问的信息 http 1.1 通信协议 
4、解析数据包
5、映射到对应的servlet
6、执行对应方法
7、返回对应的数据包

nio 通信使用
1、selector 轮询器
2、byteBuffer 数据缓存区
3、serverSocketChannel 通信管道
4、selectionKey 某个客户端的通信管道
	处理流程
		1、初始化 selector = Selector.open() byteBuffer = ByteBuffer.allocate() serverSocketChannel=ServerSocketChannel.open()
		2、绑定端口号 关闭 bio 模式（nio 默认开启 bio 模式）
		3、将 selector 注册进入 serverSocketChannel （参数 SelectionKey.OP_ACCEPT)
		4、selector.select() 返回已就绪的通信管道
		5、获取所有的客户通管道
		6、遍历客户通管道 判断某个客户端的数据是否已准备就绪（isAcceptable） 是否可读（isReadable 是否可写（isWritable）
5、缓存区
	普通缓存区
		byteBuffer
		charBuffer
		doubleBuffer
		folatBuffer
		intBuffer
		LongBuffer
		shortBuffer
		StringCharBuffer
	特殊缓存区
		DirectByteBuffer
		MappedByteBuffer
6、普通缓存区设计 
	参数
		position 
		limit
		capacity 数组大小
	init	初始化       position：0 limit:数组长度 capacity:数组长度
	read	读入数据     position:有效长度 limit:数组长度 capacity:数组长度
	filp	锁定数据     position:0 limit:有效长度 capacity:数组长度
	get	    读取数据完毕 position:有效长度 limit:有效长度 capacity:数组长度（每次迭代 position + 1）
	clear	清空缓存区   position：0 limit:数组长度 capacity:数组长度
7、特殊缓存区
	DirectByteBuffer 零拷贝缓存区
	MappedByteBuffer 直接操作文件

8、selector
	reactor 设计(未知)
