/*
simple-hash-table
Copyright (C) 2017  Wil Gaboury

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

import java.util.ArrayList;

public class HashTable<K, V>
    {
    private final int INIT_SIZE = 16;

    private class Pair <T1, T2>
        {
        private T1 first;
        private T2 second;

        public Pair(T1 first, T2 second)
            {
            this.first = first;
            this.second = second;
            }

        public T1 getFirst()
            { return first; }

        public T2 getSecond()
            { return second; }

        public void setFirst(T1 first)
            { this.first = first; }

        public void setSecond(T2 second)
            { this.second = second; }

        public String toString()
            { return "{" + first.toString() + ", " + second.toString() + "}"; }
        }

    private ArrayList<Pair<K, V> >[] table;
    private int size;

    private void alloc(int newSize)
        {
        table = new ArrayList[newSize];
        for (int i = 0; i < newSize; i++)
            { table[i] = new ArrayList<>(); }
        size = 0;
        }

    public HashTable()
        {
        size = 0;
        alloc(INIT_SIZE);
        }

    private void simpInsert(Pair<K, V> pair)
        {
        int loc = Math.abs(pair.getFirst().hashCode()) % table.length;
        for (Pair<K, V> p : table[loc])
            {
            if (p.getFirst().equals(pair.getFirst()))
                {
                p.setSecond(pair.getSecond());
                return;
                }
            }
        table[loc].add(pair);
        size++;
        }

    public void insert(K key, V value)
        {
        if ((double)size / (double)table.length > 0.75)
            { resize(table.length * 2); }
        simpInsert(new Pair<>(key, value));
        }

    private void resize(int newSize)
        {
        ArrayList<Pair<K, V> > all = new ArrayList<>();
        for (ArrayList<Pair<K, V> > ary : table)
            {
            for (Pair<K, V> p : ary)
                { all.add(p); }
            }

        alloc(newSize);
        for (Pair<K, V> p : all)
            { simpInsert(p); }
        }

    public V find(K key)
        {
        int loc = Math.abs(key.hashCode()) % table.length;
        for (Pair<K, V> p : table[loc])
            {
            if (p.getFirst().equals(key))
                { return p.getSecond(); }
            }
        return null;
        }

    private V simpRemove(K key)
        {
        int loc = Math.abs(key.hashCode()) % table.length;
        for (int i = 0; i < table[loc].size(); i++)
            {
            if (table[loc].get(i).getFirst().equals(key))
                {
                V result = table[loc].get(i).getSecond();
                table[loc].remove(i);
                size--;
                return result;
                }
            }
        return null;
        }

    public V remove(K key)
        {
        if ((double)size / (double)table.length < 0.3)
            { resize(table.length / 2); }
        return simpRemove(key);
        }

    public void clear()
        {
        size = 0;
        alloc(INIT_SIZE);
        }

    public int size()
        { return size; }

    public Pair<K, V>[] collect()
        {
        Pair<K, V>[] result = new Pair[size];
        int insert_pos = 0;
        for (ArrayList<Pair<K, V> > aryl : table)
            {
            for (Pair<K, V> p : aryl)
                {
                result[insert_pos] = p;
                insert_pos++;
                }
            }
        return result;
        }

    public String toString()
        {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        for (Pair<K, V> p : collect())
            {
            sb.append(p.toString());
            sb.append(",\n");
            }
        sb.append("}");
        return sb.toString();
        }

    private void printStructure()
        {
        int pos = 0;
        for (ArrayList<Pair<K, V> > aryl : table)
            {
            System.out.print(pos + ": ");
            for (Pair<K, V> p : aryl)
                {
                System.out.print(p + ", ");
                }
            System.out.println();
            pos++;
            }
        }

    public static void test()
        {
        HashTable<String, Integer> table = new HashTable<>();
        for (int i = 0; i < 30; i++)
            {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < i + 1; j++)
                { sb.append('a'); }
            table.insert(sb.toString(), i + 1);
            }
        System.out.println("the table");
        System.out.println(table);

        System.out.println("table distribution");
        table.printStructure();

        System.out.print("value of \"aaaaaaaaaaaaaaaaaaaaaaaaa\": ");
        System.out.println(table.find("aaaaaaaaaaaaaaaaaaaaaaaaa"));

        System.out.println("changing value of \"aaaaa\" to 100");
        table.insert("aaaaa", 100);
        System.out.print("value of \"aaaaa\": ");
        System.out.println(table.find("aaaaa"));

        System.out.println("removing value \"aaaaaaaaaa\"");
        table.remove("aaaaaaaaaa");
        System.out.print("value of \"aaaaaaaaaa\": ");
        System.out.println(table.find("aaaaaaaaaa"));

        table.clear();
        table.insert("one", 1);
        table.insert("two", 2);
        table.insert("three", 3);
        table.insert("four", 4);
        table.insert("five", 5);
        table.insert("six", 6);
        table.insert("seven", 7);
        table.insert("eight", 8);
        table.insert("nine", 9);
        table.insert("ten", 10);
        table.insert("eleven", 11);
        table.insert("twelve", 12);
        table.insert("thirteen", 13);
        table.insert("fourteen", 14);
        table.insert("fifteen", 15);
        table.insert("sixteen", 16);
        table.insert("seventeen", 17);
        table.insert("eighteen", 18);
        table.insert("nineteen", 19);
        table.insert("twenty", 20);
        table.insert("twentyone", 21);
        table.insert("twentytwo", 22);
        table.insert("twentythree", 23);
        table.insert("twentyfour", 24);
        table.insert("twentyfive", 25);
        table.insert("twentysix", 26);
        table.insert("twentyseven", 27);
        table.insert("tewntyeight", 28);
        table.insert("twentynine", 29);
        table.insert("thirty", 30);

        table.printStructure();

        //add more tests here if nessicary
        }
    }
