class Solution {
    public int singleNumber(int[] nums) {
        int seenOnce = 0, seenTwice = 0;
        for (int num : nums) {
            seenOnce = ~seenTwice & (num ^ seenOnce);
            seenTwice = ~seenOnce & (num ^ seenTwice);
        }
        return seenOnce;
    }
}
/**
 * 这个解法有点儿意思. 认为seenOnce和seenTwice构成一个counter. 这个counter记录的是一个32位长的数组,
 * 每一个位置1出现的次数. 单一个seenOnce每一个bit只能表达0, 1. 于是就只记录一个位置1出现0次, 1次.
 * 再来个seenTwice和seenOnce组合,那么就能记录一个位置的四种状态, 分别用00, 01, 10, 11来表示.
 * seenOnce中的每个bit对应被记录的32位长的数组每个位置. 如果我们想知道数组某个位置目前1出现的次数. 我们
 * 首先定位seenOnce对应的位, 然后看它是几, 再定位到seenTwice对应的位看是几, 这两个数组合出来的结果就能
 * 告诉我们目前出现了多少次. 我们可以规定00是0次, 01是一次, 10是两次, 11是三次.
 * 
 * 本质是用两个int来记录32bit数字每一位目前1出现的次数, 满三次则会清零counter这个操作.
 * 逻辑是这样的, 我们想要知道那个只出现一次的. 就要实现三次会清零.
 * 我们可以认为一个num是由32个bit组成的数. 因此它的每一位可能是0或1.
 * seenOnce和seenTwice共同起作用联合起来记录的是32位中每一位目前遇到的次数, 满三次就会清零.
 * 光有seenOnce我们就只能记录出现0次和出现1次(每个bit就是0和1). 但再来个seenTwice每一个bit就可以有
 * 四种组合也就是00, 01, 10, 11即出现0, 1, 2, 3次都能记录, 但是我们只需要记录出现零次,出现一次, 出现两次的.
 * 当出现三次的时候, 我们则是让seenOnce和seenTwice对应的位清零, 即它们代表的counter在这一位清零.
 * 我们可以想象所有出现三次的数字在它们各自的位上就会出现三次而互相抵消. 最后唯一剩下的就是那个出现一个次的.
 * 最终结果应该就是counter记录了32个bit中每一个bit出现的次数, 此时有的位置是1, 其余全是0.
 * 
 * 我们来看一下逻辑. 此时进来一个num, 有些位上是1, 有些是0. 我们不妨让seenOnce作为低位, seenTwice作为高位.
 * 那么对应一个特定的位置, seenOnce和seenTwice相应的位置构成一个2bit的二进制数字. 可以表示四种状态. 那么进来一个
 * 数字, 我们需要看是1的位目前在counter中的情况. 我们看seenOnce和seenTwice这一位的情况. 如果seenTwice是0, 那么我们
 * 可以让seenOnce + 1. + 1无非就是0变1, 1变0. 如果seenTwice是1, 说明这一位之前遇到过两次1, 此时该清零了. 于是让
 * seenOnce和seenTwice在这两个位置上都变为0即可. 接着看seenTwice, 此时需要注意的是seenOnce对应位已经更新好. 我们看
 * seenOnce, 如果看到seenOnce是1, 那说明它更新成了1, 那么自己一定是0, 否则它不会更新, 于是此时自己就不用变还是0. 如果
 * 发现seenOnce是0, 那么说明进位了或者是遇到自己是1, 它意识到要清零了. 于是如果是进位自己是0变1, 如果是清零, 自己是1变0.
 * 这样就完成了逻辑. 某个位是1的时候, 我们才可能去flip seenOnce和seenTwice对应位的bit, 如果是0就不会. 那么我们就可以认为
 * 这是一个conditional flip. 0不会flip, 1可能会flip. 那么XOR刚好满足. 在XOR的前提下,
 * 我们继续加前提也就是在什么情况下1会导致seenOnce对应位flip或者seenTwice对应位flip, 这也就是需要控制这个XOR的条件.
 * 
 * 另一种看法:
 * 以某个位为例来走一遍流程. 一开始来个num, 假设此时num在我们研究的目标位是1. 此时看seenTwice该位是1吗?
 * 此时不是, 于是让seenOnce这一位从0变为1(刚开始seenOnce和seenTwice都是所有位初始化位0). 之后更新seenTwice, 他呢
 * 就要看seenOnce上的位置, 需要注意的是此时seenOnce在这个位已经更新完毕, 我们发现seenOnce这个位是1, 于是说明
 * 这是第一次遇见. seenTwice不变还是0. 后来又来了一个num在该位是0, 那么seenOnce和seenTwice在该位都不用更新.
 * 再之后又来了一个num, 这个num在这个位置是1. 此时seenOnce需要看seenTwice在该位是否为1. 发现不是, 于是自己由1
 * 变为0(进位). 然后来到seenTwice, 注意此时seenOnce已经更新完毕, 我们发现seenOnce此时是0自己是1.
 * 明明来了一个新的1,但是此时却是0, 那就说明发生进位或者故意不变(看到seenTwice是1). 由于我们自己是0, 那就只可能是进位.
 * 于是我们就需要把seenTwice这一位由0变为1. 然后一样的道理假设又来了一个num在该位是1.
 * 此时我们仍然看seenTwice, 看我们这一位目前遇到了几次1了. 我们发现seenTwice此时是1, 说明已经遇到两次, 那么此时就需要清零了.
 * 于是seenOnce这一位本来是0, 那么现在还是0. seenTwice也是一样, 看seenOnce, 发现此时是0. 此时是0还是说明要么进位,
 * 要么故意不变. 此时我们发现seenTwice是1, 那就说明是遇到了第三次, 要清零. 于是把自己从1变成0.
 * 
 * 我们发现, seenOnce自己这一位是0变1还是1变0都是取决于seenTwice这一位的值, 如果seenTwice这一位是0,
 * 那么自己可以由0变为1(自上次清零后第一次遇到1)或者由1变为0(要进位). seenTwice反过来也要看seenOnce这一位
 * 是不是0. 如果是, 那么自己是0变为1(被进位)或者1变为0(遇到三次清零). 因为我们只有在
 * 该位是1的前提下才有可能要flip seenOnce或者seenTwice, 于是就很自然想到XOR. 但是这个flip却是由另一个同伴控制.
 * seenTwice控制当seenOnce在遇到1时, seenOnce是否可以flip,
 * seenOnce控制当seenTwice在遇到1时, seenTwice是否可以flip.
 * 自己的同伴的记录加上自己的记录才能知道整体的目前在某位遇到1的个数. 这样逻辑就出来了.
 * 
 * 这道题值得思考的地方就是这个状态的转移. 不同seenOnce和seenTwice的组合表示不同的情况(遇到1的次数), 从一个状态转移到
 * 下一个状态如何用bitwise manipulation来去表示是个关键. 当前seenOnce和seenTwice的组合如何在特定条件下转移到下一个我们
 * 想要的状态.
 * 
 * 这道题想明白就是记录32位长度的数组每个位置1出现的次数. 由于这个数组是装目标数字的.
 * 那么我们就需要另一个长度位32位的数组. 分别记录目标数字每一位1出现的
 * 次数. 然一个int刚好是32位, 可以扮演这个32位的数组. 唯一遗憾的是, int扮演的数组每一个位置只能是0和1也就是只能记录目标数字
 * 每一位要么出现一次, 要么出现0. 如果出现两次, 一个int就记不下了. 于是我们引入另一个int, 另一个int也是扮演这样的32位数组.
 * 同样每个位置只能是0或者1. 那么这两个int一组合每个位置就会出现四种组合: 00, 01, 10, 11. 这样我们就可以记录出现0次, 1次,
 * 2次, 3次的情况.
 * 
 * 第一个int
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 第二个int
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 
 * 目标数字的容器:
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 
 * 
 * 
 * 
 * 如果第一个目标数字是:
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0
 * 
 * 此时第一个int变为:
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 第二个int变为:
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0
 * 
 * 再来的目标数字假如还是:
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0
 * 
 * 第一个int:
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0
 * 第二个int:
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 
 * 这一题要我们做的是遇到三次就清零. 我们接着看:
 * 
 * 第三个来的数字:
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0
 * 
 * 第一个int:
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 第二个int:
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 
 * 第四个数字:
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0
 * 
 * 第一个int:
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 
 * 第二个int:
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0
 * 这样等于是又回来了.
 * 
 * 很明显的特征就是让目标数字某位是1的时候, 第二个int如果在该位是0, 那么flip第一个int
 * 在该位的值. 之后第一个int如果此时0, 那么flip第二个int在该位的值. 我们是先判断
 * 要不要flip第二个int(低位的), 再看是不是要flip第一个int(高位的).
 * 
 * 我们可以发现, 把两个int放在一起, 竖着看就能得到目前某一位置遇到1的个数是多少.
 * 选择某一位竖着看是关键. 竖着看圈起来的部分就是表示这一位遇到1的次数的二进制表示. 就是用
 * 二进制表示次数一样. 第一个int代表高位, 第二个int代表低位. int的数量越多, 我们能表示
 * 的数字越多(二进制数的长度变长自然可以表示的数字变多). 本质就是使用二进制数字表示某一位
 * 1目前遇到的次数. 二进制数字由不同个int的在该位的值构成. 每一个int在该位置的值构成该二进制
 * 数字的一位, 有几个int, 这个二进制数就会有几位. 有n个int, 就能表示2^n种情况, 也就是
 * 对于一个长32位, 疯狂进来长度为32位的数字的位置, 对于其中每个位置最多能记录(2^n) - 1 次1的出现.
 * 
 * 这道题相对直接, 就是seenTwice掌控seenOnce是否能flip, seenOnce掌控seenTwice是否能flip.
 * 
 * 我们想记录的是每一位上1出现的次数. 除了用一个数组可以记录, 就可以用若干个int来记录因为int天生就是
 * 32位, 只是每一位只能是0和1因此我们需要多个int.
 * 
 * 只有是1才可以flip, 0不行哦. 于是决定了使用XOR, 但是有条件才能使用XOR哦, 于是决定了&的使用.
 * 也就是在满足某种条件下才能使用XOR.
 * 
 * 时间复杂度: O(n)
 * 空间复杂度: O(1)
 * 
 */