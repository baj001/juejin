package com.shang.javatest.LeetCode;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * @author baj
 * @creat 2022-05-18 18:22
 */
import java.util.Scanner;
/*
 * 链表的输入输出(以反转链表为例)
 * */
public class Main {
    //创建ListNode
    static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    //迭代反转链表代码
    public static ListNode reverseList(ListNode head) {
        if(head == null) return null;
        ListNode pre = null;
        ListNode tmp = null;
        ListNode cur = head;
        while(cur != null){
            tmp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = tmp;
        }
        return pre;
    }

    public static void main(String[] args) {
        //按照1，2，3，4，5的输入来构建链表
        Scanner sc = new Scanner(System.in);
        String[] s = sc.nextLine().split(",");
        ListNode head = new ListNode(Integer.parseInt(s[0]));
        ListNode cur = head;
        for (int i = 1; i < s.length; i++) {
            cur.next = new ListNode(Integer.parseInt(s[i]));
            cur = cur.next;
        }
        //得到反转之后的结果5,4,3,2,1
        ListNode root = reverseList(head);
        //打印结果54321
        while (root != null) {
            System.out.print(root.val);
            root = root.next;
        }
    }
}
//public class  Main{
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        while (scanner.hasNext()) {
//            int n = scanner.nextInt();
//            int[] arr = new int[n];
//            for (int i = 0; i < arr.length; i++) {
//                arr[i] = i;
//            }
//            System.out.println(delete(arr));
//        }
//        scanner.close();
//    }
//
//    public static int delete (int[] arr) {
//        Queue<Integer> queue = new LinkedList<>();
//        for (int i = 0; i < arr.length; i++) {
//            queue.add(arr[i]);
//        }
//        while (queue.size() != 1) {
//            int count = 0;
//            while (count != 2) {
//                queue.add(queue.peek());
//                queue.poll();
//                count++;
//            }
//            queue.poll();
//        }
//        return queue.element();
//    }
//}