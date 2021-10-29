package com.yiben_video.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 王飞
 * @create 2021/10/26 15:16
 * @emai 936886169@qq.com
 */
public class test {
    public static void main(String[] args) {
        //国节点 中国
        Node rootNode = new Node();
        rootNode.setNodeId(110000);
        rootNode.setNodeName("中国");
        rootNode.setParentNodeId(0);
        rootNode.setTime(System.currentTimeMillis());

        //省节点 广东
        Node pNode1 = new Node();
        pNode1.setNodeId(120000);
        pNode1.setNodeName("广东");
        pNode1.setParentNodeId(110000);
        pNode1.setTime(System.currentTimeMillis());

        //市节点 广州
        Node cNode1 = new Node();
        cNode1.setNodeId(120001);
        cNode1.setNodeName("广州");
        cNode1.setParentNodeId(120000);
        cNode1.setTime(System.currentTimeMillis());

        //区节点 广州
        Node aNode1 = new Node();
        aNode1.setNodeId(1200011);
        aNode1.setNodeName("天河区");
        aNode1.setParentNodeId(120001);
        aNode1.setTime(System.currentTimeMillis());

        //子节点 湖南
        Node pNode2 = new Node();
        pNode2.setNodeId(130000);
        pNode2.setNodeName("湖南");
        pNode2.setParentNodeId(110000);
        pNode2.setTime(System.currentTimeMillis());

        //市节点 长沙
        Node cNode2 = new Node();
        cNode2.setNodeId(130001);
        cNode2.setNodeName("长沙");
        cNode2.setParentNodeId(130000);
        cNode2.setTime(System.currentTimeMillis());

        //子节点 上海
        Node pNode3 = new Node();
        pNode3.setNodeId(140000);
        pNode3.setNodeName("上海");
        pNode3.setParentNodeId(110000);
        pNode3.setTime(System.currentTimeMillis());

        List<Node> nodeList = new ArrayList<>();
        nodeList.add(rootNode);
        nodeList.add(pNode1);
        nodeList.add(cNode1);
        nodeList.add(aNode1);
        nodeList.add(pNode2);
        nodeList.add(cNode2);
        nodeList.add(pNode3);

        //转换
        List<TreeDot<Node>> nodeTreeDotList = TreeDotUtils.convertListToTreeDot(nodeList);

        Map<String,Object> result = new HashMap<>();
        result.put("nodeTreeDotList",nodeTreeDotList);
        System.out.println(result);
    }
}
