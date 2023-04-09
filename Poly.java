class Poly
{
    private Term first;
    private Term last;
    private class Term
    {
        private int coef;
        private int expo;
        private Term next;

        private Term(int coef, int expo, Term next)
        {
            this.coef = coef;
            this.expo = expo;
            this.next = next;
        }
    }
    public Poly()
    {
        first = new Term(0, Integer.MAX_VALUE, null);
        last = first;
    }
    public boolean isZero()
    {
        return first.next == null;
    }
    public Poly minus()
    {
        Poly result = new Poly();
        Term temp = first.next;
        while (temp != null)
        {
            int newCoef = -temp.coef;
            int expo = temp.expo;
            Term newTerm = new Term(newCoef, expo, null);
            result.last.next = newTerm;
            result.last = newTerm;
            temp = temp.next;
        }
        return result;
    }
    public Poly plus(Poly that)
    {
        Poly result = new Poly();
        Term left = this.first.next;
        Term right = that.first.next;
        while (left != null && right != null)
        {
            if (left.expo > right.expo)
            {
                result.last.next = new Term(left.coef, left.expo, null);
                result.last = result.last.next;
                left = left.next;
            }
            else if (right.expo > left.expo)
            {
                result.last.next = new Term(right.coef, right.expo, null);
                result.last = result.last.next;
                right = right.next;
            }
            else
            {
                int sum = left.coef + right.coef;
                if (sum != 0)
                {
                    result.last.next = new Term(sum, left.expo, null);
                    result.last = result.last.next;
                }
                left = left.next;
                right = right.next;
            }
        }
        if (left != null)
        {
            result.last.next = left;
            result.last = this.last;
        }
        else if (right != null)
        {
            result.last.next = right;
            result.last = that.last;
        }
        return result;
    }
    public Poly plus(int coef, int expo)
    {
        if (coef == 0)
        {
            throw new IllegalArgumentException("Coefficient is zero.");
        }
        if (expo < 0)
        {
            throw new IllegalArgumentException("Exponent is negative.");
        }
        if (last != null && expo >= last.expo)
        {
            throw new IllegalArgumentException("Exponent is not greater than the exponent of the last term");
        }
        Term newTerm = new Term(coef, expo, null);
        if (last == null)
        {
            first = last = newTerm;
        }
        else
        {
            last.next = newTerm;
            last = newTerm;
        }
        return this;
    }
    public String toString()
    {
        StringBuilder str = new StringBuilder();
        Term temp = first.next;
        boolean isFirstTerm = true;
        while (temp != null)
        {
            if (!isFirstTerm && temp.coef > 0)
            {
                str.append("+");
            }
            if (temp.coef != 1 || temp.expo == 0)
            {
                str.append(temp.coef);
            }
            if (temp.expo > 0)
            {
                str.append("x");
                if (temp.expo > 1)
                {
                    str.append("^" + temp.expo);
                }
            }
            isFirstTerm = false;
            temp = temp.next;
        }
        if (str.length() == 0)
        {
            str.append("0");
        }
        return str.toString();
    }
    public static void main(String[] args)
    {
        Poly p = new Poly().plus(3,5).plus(2,4).plus(2,3).plus(-1,2).plus(5,0);
        Poly q = new Poly().plus(7,4).plus(1,2).plus(-4,1).plus(-3,0);
        Poly z = new Poly();
        Poly r = new Poly().plus(1, 1);
        Poly s = new Poly().plus(1, 1).plus(1, 0);

        System.out.println(p);                 // 3x⁵ + 2x⁴ + 2x³ - 1x² + 5x⁰
        System.out.println(q);                 // 7x⁴ + 1x² - 4x¹ - 3x⁰
        System.out.println(z);                 // 0
        System.out.println(r);                 // 1x¹
        System.out.println(s);                 // 1x¹ + 1x⁰

        System.out.println(p.minus());         // -3x⁵ - 2x⁴ - 2x³ + 1x² - 5x⁰
        System.out.println(q.minus());         // -7x⁴ - 1x² + 4x¹ + 3x⁰
        System.out.println(z.minus());         // 0
        System.out.println(r.minus());         // -1x¹

        System.out.println(p.plus(q));         // 3x⁵ + 9x⁴ + 2x³ - 4x¹ + 2x⁰
        System.out.println(p.plus(z));         // 3x⁵ + 2x⁴ + 2x³ - 1x² + 5x⁰
        System.out.println(p.plus(q.minus())); // 3x⁵ - 5x⁴ + 2x³ - 2x² + 4x¹ + 8x⁰
        System.out.println(p.plus(r.minus())); // 3x⁵ + 2x⁴ + 2x³ - 1x² - 1x^0 + 5x⁰
        System.out.println(p.plus(z));         // 3x⁵ + 2x⁴ + 2x³ - 1x² + 5x⁰
    }
}