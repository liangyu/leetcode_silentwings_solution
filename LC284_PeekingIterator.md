# LC284 Peeking Iterator

标签（空格分隔）： LeetCode Java

---

【题目】

    /**
     *Given an Iterator class interface with methods: next() and hasNext(), design and implement a PeekingIterator
     * that support the peek() operation -- it essentially peek() at the element that will be returned by the next
     * call to next().
     *
     * Follow up: How would you extend your design to be generic and work with all types, not just integer?
     *
     * @param iterator
     */

【思路】

用一个next指针提前缓存iterator中的下一个元素，如果有值则为iter.next()，没有则为null。这样的话，peek() = next；hasNext() 就是判断next是否为null即可。

【解答】

```java     
// Java Iterator interface reference:
// https://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html

// time = O(1), space = O(1)
class PeekingIterator implements Iterator<Integer> {
    private Iterator<Integer> iter;
    private Integer next; // 创建一个next指针
    public PeekingIterator(Iterator<Integer> iterator) {
        // initialize any member here.
        iter = iterator;
        if (iter.hasNext()) next = iterator.next();
    }

    // Returns the next element in the iteration without advancing the iterator.
    public Integer peek() {
        return next;
    }

    // hasNext() and next() should behave the same as in the Iterator interface.
    // Override them if needed.
    @Override
    public Integer next() {
        Integer res = next; // cache next的值
        next = iter.hasNext() ? iter.next() : null;
        return res;
    }

    @Override
    public boolean hasNext() {
        return next != null;
    }
}
```

The Follow Up

For the most part, our code would work fine if we replaced integers with another data type (e.g. strings).

There is one case where this does not work, and that's if the underlying Iterator might return null/ None from .next(...) as an actual value. If our code is using null to represent an exhausted Iterator, or to represent that we don't currently have a peeked value stored away (as in Approach 1), then the conditionals in PeekingIterator will not behave as expected on these values coming out of the underlying Iterator.

We can solve it by using separate boolean variables to state whether or not there's currently a peeked value or the Iterator is exhausted, instead of trying to infer this information based on null status of value variables.

In Java, you can also use generics on your Iterator.

