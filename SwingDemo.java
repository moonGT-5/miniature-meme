//https://github.com/moonGT-5/congenial-telegram.git
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class SwingDemo {

    private static final int MaxInt = 22222;

    public static void main(String[] args) {
        Graph graph = new Graph();
        createUDN(graph);
        replenish(graph);
        createGUI(graph);
    }

    public static void createGUI(Graph graph) {
        JFrame frame = new JFrame("导游系统");
        frame.setSize(800, 200);
        frame.setLocation(200, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 1));
        JLabel label = new JLabel("欢迎来到河南农业大学许昌校区", JLabel.CENTER);
        panel.add(label);
        JButton[] buttons = new JButton[8];
        String[] texts = { "查询景点信息", "查询景点路线", "修改景点信息", "增加景点信息", "增加新的路径", "删除景点信息", "删除原有路径", "退出" };
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(texts[i]);
            buttons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton button = (JButton) e.getSource();
                    String text = button.getText();
                    switch (text) {
                        case "查询景点信息":
                            attractionSelection(frame, graph, 1);
                            break;
                        case "查询景点路线":
                            attractionSelection(frame, graph, 2);
                            break;
                        case "修改景点信息":
                            attractionSelection(frame, graph, 3);
                            break;
                        case "增加景点信息":
                            attractionSelection(frame, graph, 4);
                            break;
                        case "增加新的路径":
                            attractionSelection(frame, graph, 5);
                            break;
                        case "删除景点信息":
                            attractionSelection(frame, graph, 6);
                            break;
                        case "删除原有路径":
                            attractionSelection(frame, graph, 7);
                            break;
                        case "退出":
                            System.exit(0);
                            break;
                        default:
                            JOptionPane.showMessageDialog(frame, "无效的选项", "错误", JOptionPane.ERROR_MESSAGE);
                            break;
                    }
                }
            });
            panel.add(buttons[i]);
        }
        frame.add(panel);
        frame.setVisible(true);
    }

    public static void attractionSelection(JFrame frame, Graph graph, int n) {
        JFrame contentPane = (JFrame) SwingUtilities.getWindowAncestor(frame.getContentPane());
        contentPane.getContentPane().removeAll();
        JPanel vertexPanel = new JPanel(new GridLayout(graph.vexnum, 1));
        for (int i = 0; i < graph.vexnum; i++) {
            JLabel label = new JLabel(String.format("%2d. %s", i + 1, graph.vexs[i].name));
            vertexPanel.add(label);
        }
        JScrollPane scrollPane = new JScrollPane(vertexPanel);
        contentPane.add(scrollPane);
        JPanel buttonPanel = new JPanel();
        JButton selectButton = new JButton("选择景点");
        JButton returnButton = new JButton("返回");
        JLabel detailLabel = new JLabel();
        if (n == 1) {
            selectButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String input = JOptionPane.showInputDialog(contentPane, "请输入景点编号(1-" + graph.vexnum + "):", "景点选择",
                            JOptionPane.QUESTION_MESSAGE);
                    int select;
                    try {
                        select = Integer.parseInt(input);
                    } catch (NumberFormatException e1) {
                        JOptionPane.showMessageDialog(contentPane, "请输入一个有效的整数景点编号。");
                        return;
                    }
                    if (select >= 1 && select <= graph.vexnum) {
                        contentPane.getContentPane().removeAll();
                        vertexPanel.removeAll();
                        String detailInfo = graph.vexs[select - 1].name + " - " + graph.vexs[select - 1].info;
                        JLabel detailLabel = new JLabel(detailInfo);
                        contentPane.add(detailLabel, BorderLayout.NORTH);
                        contentPane.add(scrollPane);
                        contentPane.add(buttonPanel, BorderLayout.SOUTH);
                        contentPane.revalidate();
                        contentPane.repaint();
                    }
                }
            });
        }
        if (n == 2) {
            selectButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    String input1 = JOptionPane.showInputDialog(contentPane, "请输入当前景点编号(1-" + graph.vexnum + "):",
                            "景点选择",
                            JOptionPane.QUESTION_MESSAGE);
                    String input2 = JOptionPane.showInputDialog(contentPane, "请输入目的地景点编号(1-" + graph.vexnum + "):",
                            "景点选择",
                            JOptionPane.QUESTION_MESSAGE);
                    int initialPosition;
                    int targetPosition;
                    try {
                        initialPosition = Integer.parseInt(input1);
                        targetPosition = Integer.parseInt(input2);
                    } catch (NumberFormatException e1) {

                        JOptionPane.showMessageDialog(contentPane, "请输入一个有效的整数景点编号。");
                        return;
                    }
                    if (initialPosition >= 1 && initialPosition <= graph.vexnum && targetPosition >= 1
                            && targetPosition <= graph.vexnum) {
                        contentPane.getContentPane().removeAll();
                        vertexPanel.removeAll();
                        JTextArea textArea = new JTextArea();
                        textArea.setEditable(false);
                        textArea.setText(ShortestPath_DIJ(graph, initialPosition, targetPosition));
                        contentPane.add(textArea, BorderLayout.NORTH);
                        contentPane.add(scrollPane);
                        contentPane.add(buttonPanel, BorderLayout.SOUTH);
                        contentPane.revalidate();
                        contentPane.repaint();
                    }
                }
            });
        }
        if (n == 3) {
            selectButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String input = JOptionPane.showInputDialog(contentPane, "请输入景点编号(1-" + graph.vexnum + "):", "景点选择",
                            JOptionPane.QUESTION_MESSAGE);
                    String input2 = JOptionPane.showInputDialog(contentPane, "请输入该景点的修改信息：", "景点选择",
                            JOptionPane.QUESTION_MESSAGE);
                    int select;
                    try {
                        select = Integer.parseInt(input);
                    } catch (NumberFormatException e1) {

                        JOptionPane.showMessageDialog(contentPane, "请输入一个有效的整数景点编号。");
                        return;
                    }
                    if (select >= 1 && select <= graph.vexnum) {
                        contentPane.getContentPane().removeAll();
                        vertexPanel.removeAll();
                        graph.vexs[select - 1].info = input2;
                        String detailInfo = "修改成功";
                        detailLabel.setText(detailInfo);
                        contentPane.add(detailLabel, BorderLayout.NORTH);
                        contentPane.add(scrollPane);
                        contentPane.add(buttonPanel, BorderLayout.SOUTH);
                        contentPane.revalidate();
                        contentPane.repaint();
                    }
                }
            });
        }
        if (n == 4) {
            selectButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    String input = JOptionPane.showInputDialog(contentPane, "请输入景点编号:", "景点选择",
                            JOptionPane.QUESTION_MESSAGE);
                    String input2 = JOptionPane.showInputDialog(contentPane, "请输入该景点的名称：", "景点选择",
                            JOptionPane.QUESTION_MESSAGE);
                    String input3 = JOptionPane.showInputDialog(contentPane, "请输入该景点的信息：", "景点选择",
                            JOptionPane.QUESTION_MESSAGE);
                    int select;
                    try {
                        select = Integer.parseInt(input);
                    } catch (NumberFormatException e1) {
                        JOptionPane.showMessageDialog(contentPane, "请输入一个有效的整数景点编号。");
                        return;
                    }
                    if (select > graph.vexnum) {
                        contentPane.getContentPane().removeAll();
                        vertexPanel.removeAll();
                        graph.vexnum++;
                        graph.vexs[graph.vexnum - 1] = new VertexType(input2, input3);
                        String detailInfo = "添加成功";
                        detailLabel.setText(detailInfo);
                        contentPane.add(detailLabel, BorderLayout.NORTH);
                        contentPane.add(scrollPane);
                        contentPane.add(buttonPanel, BorderLayout.SOUTH);
                        contentPane.revalidate();
                        contentPane.repaint();
                    }
                }
            });
        }
        if (n == 5) {
            selectButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    String input = JOptionPane.showInputDialog(contentPane, "请输入路径的始点+ (1-" + graph.vexnum + "):",
                            "景点选择",
                            JOptionPane.QUESTION_MESSAGE);
                    String input2 = JOptionPane.showInputDialog(contentPane, "请输入路径的终点+ (1-" + graph.vexnum + ")",
                            "景点选择",
                            JOptionPane.QUESTION_MESSAGE);
                    String input3 = JOptionPane.showInputDialog(contentPane, "请输入始点和终点的距离：", "景点选择",
                            JOptionPane.QUESTION_MESSAGE);
                    int initial;
                    int destination;
                    int distance;
                    try {
                        initial = Integer.parseInt(input);
                        destination = Integer.parseInt(input2);
                        distance = Integer.parseInt(input3);
                    } catch (NumberFormatException e1) {
                        JOptionPane.showMessageDialog(contentPane, "对不起!请输入数字!");
                        return;
                    }
                    if ((0 < initial && initial < graph.vexnum + 1)
                            && (0 < destination && destination < graph.vexnum + 1)) {
                        contentPane.getContentPane().removeAll();
                        vertexPanel.removeAll();
                        graph.arcs[initial - 1][destination - 1] = distance;
                        graph.arcs[destination - 1][initial - 1] = distance;
                        String detailInfo = "添加路径成功";
                        detailLabel.setText(detailInfo);
                        contentPane.add(detailLabel, BorderLayout.NORTH);
                        contentPane.add(scrollPane);
                        contentPane.add(buttonPanel, BorderLayout.SOUTH);
                        contentPane.revalidate();
                        contentPane.repaint();
                    }
                }
            });
        }
        if (n == 6) {
            selectButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String input = JOptionPane.showInputDialog(contentPane, "请输入景点编号:", "景点选择",
                            JOptionPane.QUESTION_MESSAGE);
                    int select;
                    try {
                        select = Integer.parseInt(input);
                    } catch (NumberFormatException e1) {
                        JOptionPane.showMessageDialog(contentPane, "请输入一个有效的整数景点编号。");
                        return;
                    }
                    if (0 < select && select < graph.vexnum + 1) {
                        contentPane.getContentPane().removeAll();
                        vertexPanel.removeAll();
                        graph.vexs[select - 1] = null;
                        for (int h = select - 1; h < graph.vexnum; h++) {
                            graph.vexs[h] = graph.vexs[h + 1];
                        }
                        for (int w = select - 1; w < graph.vexnum; w++)
                            for (int j = 0; j < graph.arcs[w].length; j++) {
                                graph.arcs[w][j] = graph.arcs[w + 1][j];
                                graph.arcs[j][w] = graph.arcs[j][w + 1];
                            }
                        graph.vexnum--;
                        String detailInfo = "删除成功";
                        detailLabel.setText(detailInfo);
                        contentPane.add(detailLabel, BorderLayout.NORTH);
                        contentPane.add(scrollPane);
                        contentPane.add(buttonPanel, BorderLayout.SOUTH);
                        contentPane.revalidate();
                        contentPane.repaint();
                    }
                }
            });
        }
        if (n == 7) {
            selectButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String input = JOptionPane.showInputDialog(contentPane,
                            "请输入将要删除路径的始点标号" + "(1-" + graph.vexnum + "):",
                            JOptionPane.QUESTION_MESSAGE);
                    String input2 = JOptionPane.showInputDialog(contentPane,
                            "请输入将要删除路径的终点标号" + "(1-" + graph.vexnum + "):",
                            JOptionPane.QUESTION_MESSAGE);
                    int initial;
                    int destination;
                    try {
                        initial = Integer.parseInt(input);
                        destination = Integer.parseInt(input2);
                    } catch (NumberFormatException e1) {
                        JOptionPane.showMessageDialog(contentPane, "请输入一个有效的整数景点编号。");
                        return;
                    }
                    if ((0 < initial && initial < graph.vexnum + 1)
                            && (0 < destination && destination < graph.vexnum + 1)) {
                        contentPane.getContentPane().removeAll();
                        vertexPanel.removeAll();
                        graph.arcs[initial - 1][destination - 1] = MaxInt;
                        graph.arcs[destination - 1][initial - 1] = MaxInt;
                        String detailInfo = "删除成功";
                        detailLabel.setText(detailInfo);
                        contentPane.add(detailLabel, BorderLayout.NORTH);
                        contentPane.add(scrollPane);
                        contentPane.add(buttonPanel, BorderLayout.SOUTH);
                        contentPane.revalidate();
                        contentPane.repaint();
                    }
                }
            });
        }
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(contentPane, "您确定要返回主界面吗？", "确认", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    createGUI(graph);
                }
            }
        });
        buttonPanel.add(selectButton);
        buttonPanel.add(returnButton);
        buttonPanel.add(detailLabel);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        contentPane.revalidate();
        contentPane.repaint();
    }

    public static void printf(Object o) {
        System.out.println(o);
    }

    public static void createUDN(Graph graph) {
        try {
            String encoding = "utf-8";
            File file = new File("D:\\workspace\\code\\congenial-telegram\\校园导航系统\\map.txt");
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                int i = 1;
                int e, f;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    String[] a = lineTxt.split(" ");
                    if (i == 1) {
                        graph.vexnum = Integer.parseInt(a[0]);
                        graph.arcnum = Integer.parseInt(a[1]);
                    }
                    if (1 < i && i <= (graph.vexnum + 1)) {
                        graph.vexs[i - 2] = new VertexType(a[0], a[1]);
                    }
                    if (i <= (graph.arcnum + graph.vexnum + 1) && i > (graph.vexnum + 1)) {
                        e = LocateVex(graph, a[0]);
                        f = LocateVex(graph, a[1]);
                        graph.arcs[e][f] = Integer.parseInt(a[2]);
                        graph.arcs[f][e] = Integer.parseInt(a[2]);
                    }
                    i++;
                }
                read.close();
            }
        } catch (Exception e) {
            printf(e.getMessage());
        }
    }

    public static void replenish(Graph graph) {
        for (int h = 0; h < graph.arcs.length; h++)
            for (int j = 0; j < graph.arcs[h].length; j++)
                if (h != j && graph.arcs[h][j] == 0) {
                    graph.arcs[h][j] = MaxInt;
                    graph.arcs[j][h] = MaxInt;
                }
    }

    public static int LocateVex(Graph graph, String name) {
        for (int i = 0; i < graph.vexnum; i++)
            if (name.equals(graph.vexs[i].name))
                return i;
        return -1;
    }

    public static String ShortestPath_DIJ(Graph graph, int v0, int v1) {
        v0 -= 1;
        v1 -= 1;
        StringBuilder sb = new StringBuilder();
        int n = graph.vexnum;
        boolean[] S = new boolean[n];
        int[] D = new int[n];
        int[] Path = new int[n];
        int i, j, v = 0, w = 0;
        int min;
        for (i = 0; i < n; i++) {
            S[i] = false;
            D[i] = graph.arcs[v0][i];
            if (D[i] < MaxInt)
                Path[i] = v0;
            else
                Path[i] = -1;
        }
        D[v0] = 0;
        S[v0] = true;
        for (i = 1; i < n + 1; i++) {
            min = MaxInt;
            for (w = 0; w < n; w++) {
                if (!S[w] && D[w] < min) {
                    v = w;
                    min = D[w];
                }
            }
            S[v] = true;
            for (j = 0; j < n; j++) {
                if (!S[j] && (min + graph.arcs[v][j] < D[j])) {
                    D[j] = min + graph.arcs[v][j];
                    Path[j] = v;
                }
            }
        }
        int next;
        if (D[v1] < MaxInt && v1 != v0) {
            sb.append("->" + graph.vexs[v1].name);
            next = Path[v1];
            while (next != v0) {
                sb.insert(0, "->" + graph.vexs[next].name);
                next = Path[next];
            }
            sb.insert(0, graph.vexs[v0].name);
            sb.append("最短距离为：" + D[v1]);
        } else if (v1 != v0) {
            sb.append(graph.vexs[v1].name + "<-" + graph.vexs[v0].name + "no path:");
        }
        return sb.toString();
    }
}