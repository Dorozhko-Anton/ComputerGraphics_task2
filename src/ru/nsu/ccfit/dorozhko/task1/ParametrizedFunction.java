package ru.nsu.ccfit.dorozhko.task1;

import ru.nsu.ccfit.dorozhko.MainFrame;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import static java.awt.Color.BLUE;

/**
 * Created by Anton on 10.03.14.
 */
public class ParametrizedFunction {
    private final ParametrizedFunctionPanel funcPanel;

    /**
     * Ctor that manages adding menu items, actions, canvas to the mainFrame.
     * May be I should do Init() method instead.
     */
    public ParametrizedFunction() {
        final MainFrame mainFrame = new MainFrame("Лабораторная работа №2");

        funcPanel = new ParametrizedFunctionPanel();
        mainFrame.addCanvas(funcPanel);


        AbstractAction moveUp = new AbstractAction("Вверх", MainFrame.createImageIcon("/images/up.png")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                funcPanel.setY0(funcPanel.getY0() + funcPanel.getMoveStep());
            }
        };
        AbstractAction moveDown = new AbstractAction("Вниз", MainFrame.createImageIcon("/images/down.png")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                funcPanel.setY0(funcPanel.getY0() - funcPanel.getMoveStep());
            }
        };
        AbstractAction moveRight = new AbstractAction("Вправо", MainFrame.createImageIcon("/images/right.png")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                funcPanel.setX0(funcPanel.getX0() - funcPanel.getMoveStep());
            }
        };
        AbstractAction moveLeft = new AbstractAction("Влево", MainFrame.createImageIcon("/images/left.png")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                funcPanel.setX0(funcPanel.getX0() + funcPanel.getMoveStep());
            }
        };

        mainFrame.addAction(moveUp);
        mainFrame.addAction(moveDown);
        mainFrame.addAction(moveLeft);
        mainFrame.addAction(moveRight);

        // scale + -
        AbstractAction decreaseScale = new AbstractAction("Уменьшить", MainFrame.createImageIcon("/images/dec.png")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (funcPanel.getUnitsX() - funcPanel.getScaleStep() > 10
                        && funcPanel.getUnitsY() - funcPanel.getScaleStep() > 10) {
                    funcPanel.setUnitsX(funcPanel.getUnitsX() - funcPanel.getScaleStep());
                    funcPanel.setUnitsY(funcPanel.getUnitsY() - funcPanel.getScaleStep());
                }
            }
        };
        AbstractAction increaseScale = new AbstractAction("Увеличить", MainFrame.createImageIcon("/images/inc.png")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                funcPanel.setUnitsX(funcPanel.getUnitsX() + funcPanel.getScaleStep());
                funcPanel.setUnitsY(funcPanel.getUnitsY() + funcPanel.getScaleStep());
            }
        };
        mainFrame.addAction(decreaseScale);
        mainFrame.addAction(increaseScale);


        // option panel

        final JCheckBox dragAndDropPermission = new JCheckBox("Drag'n'Drop");
        dragAndDropPermission.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                funcPanel.turnDragAndDrop(e.getStateChange() == ItemEvent.SELECTED);
            }
        });
        final SpinnerNumberModel stepSize = new SpinnerNumberModel(10, 5, 50, 1);
        stepSize.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                funcPanel.setMoveStep((Integer) stepSize.getNumber());
            }
        });

        final JCheckBox wheelPermission = new JCheckBox("Включить прокрутку колесиком");
        wheelPermission.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                funcPanel.turnScaleByWheel(e.getStateChange() == ItemEvent.SELECTED);
            }
        });
        final SpinnerNumberModel scaleStepSize = new SpinnerNumberModel(10, 5, 50, 1);
        scaleStepSize.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                funcPanel.setScaleStep((Integer) scaleStepSize.getNumber());
            }
        });

        final JPanel optionPanel = new JPanel(new GridLayout(2, 1));

        TitledBorder scaleBorder = BorderFactory.createTitledBorder("Масштаб");
        JPanel scalePanel = new JPanel(new GridLayout(2, 1));
        scalePanel.add(new JLabel("Шаг"));
        scalePanel.add(new JSpinner(scaleStepSize));
        scalePanel.add(wheelPermission);
        scalePanel.setBorder(scaleBorder);

        optionPanel.add(scalePanel);

        TitledBorder movementBorder = BorderFactory.createTitledBorder("Движение");
        JPanel movementPanel = new JPanel(new GridLayout(2, 1));
        movementPanel.add(new JLabel("Шаг"));
        movementPanel.add(new JSpinner(stepSize));
        movementPanel.add(dragAndDropPermission);
        movementPanel.setBorder(movementBorder);

        optionPanel.add(movementPanel);


        AbstractAction showOptionsPane = new AbstractAction("Настройки", MainFrame.createImageIcon("/images/options.png")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showConfirmDialog(mainFrame, optionPanel, "Настройки", JOptionPane.YES_NO_OPTION);
            }
        };
        mainFrame.addAction(showOptionsPane);

        AbstractAction returnToDefaults =
                new AbstractAction("Сбросить изменения", MainFrame.createImageIcon("/images/reset.png")) {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        funcPanel.setX0(400);
                        funcPanel.setY0(300);
                        funcPanel.setUnitsX(20);
                        funcPanel.setUnitsY(20);
                    }
                };
        mainFrame.addAction(returnToDefaults);

        JMenu navigation = new JMenu("Навигация");
        mainFrame.addMenu(navigation);

        navigation.add(moveUp);
        navigation.add(moveDown);
        navigation.add(moveRight);
        navigation.add(moveLeft);
        navigation.add(decreaseScale);
        navigation.add(increaseScale);
        navigation.add(showOptionsPane);
        navigation.add(returnToDefaults);


        AbstractAction contacts = new AbstractAction("Контакты", MainFrame.createImageIcon("/images/contact.png")) {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(mainFrame, "name: Dorozhko Anton \n mail: dorozhko.a@gmail.com\n ");
            }
        };
        mainFrame.addAction(contacts);
        mainFrame.getAboutMenu().add(contacts);

        AbstractAction aboutAction = new AbstractAction("О программе", MainFrame.createImageIcon("/images/help_info2.png")) {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(mainFrame, "Лабораторная работа №2s");
            }
        };
        mainFrame.addAction(aboutAction);
        mainFrame.getAboutMenu().add(aboutAction);
        AbstractAction exitAction = new AbstractAction("Выход", MainFrame.createImageIcon("/images/exit.png")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };
        mainFrame.addAction(exitAction);
        mainFrame.getFileMenu().add(exitAction);
        mainFrame.setVisible(true);
    }

    /**
     * Panel for plotting function.
     * Provides origin relative put Pixel, can pain Grid, can scale.
     */
    private class ParametrizedFunctionPanel extends JPanel {
        private int x0 = 400;
        private int y0 = 300;
        private int unitsX = 20;
        private int unitsY = 20;
        private int moveStep = 10;
        private double maxX = 100;
        private double minX = -100;
        private double maxY = 100;
        private double minY = -100;
        private int scaleStep = 10;
        private Point2D mousePt;

        private BufferedImage canvas;


        private final MouseAdapter getPoint = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    mousePt = e.getPoint();
                }
            }

        };
        private final MouseMotionAdapter dragAndDropAxis = new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {

                    double maxX = ((getSize().getWidth() - (x0 + e.getX() - mousePt.getX())) / unitsX);
                    double minX = -(double) (x0 + e.getX() - mousePt.getX()) / unitsX;

                    double minY = (-(getSize().getHeight() - (y0 + e.getY() - mousePt.getY())) / unitsY);
                    double maxY = (double) (y0 + e.getY() - mousePt.getY()) / unitsY;


                    if (maxX < 40
                            && minX > -40) {
                        x0 += e.getX() - mousePt.getX();
                    }
                    if (maxY < 40
                            && minY > -40) {
                        y0 += e.getY() - mousePt.getY();
                    }

                    mousePt = e.getPoint();
                    repaint();
                }

            }
        };
        private final MouseWheelListener scale = new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {

                int oldUnitsX = unitsX;
                int oldUnitsY = unitsY;

                if (unitsX - Math.signum(e.getUnitsToScroll()) * scaleStep > 15
                        && unitsX - Math.signum(e.getUnitsToScroll()) * scaleStep < 500) {
                    unitsX -= Math.signum(e.getUnitsToScroll()) * scaleStep;
                }
                if (unitsY - Math.signum(e.getUnitsToScroll()) * scaleStep > 15
                        && unitsY - Math.signum(e.getUnitsToScroll()) * scaleStep < 500) {
                    unitsY -= Math.signum(e.getUnitsToScroll()) * scaleStep;
                }
                x0 = e.getX() - (e.getX() - x0) * unitsX / oldUnitsX;
                y0 = e.getY() - (e.getY() - y0) * unitsY / oldUnitsY;

                repaint();

            }
        };

        {
            setSize(800, 600);
        }

        /**
         * Set pixel relative to origin
         *
         * @param x - x coord
         * @param y - y coord
         * @param g - bufferedImage
         */
        private void putPixel(int x, int y, BufferedImage g) {
            //g.fillRect(x0 + x, y0 - y, 1, 1);
            if ((x0 + x < g.getWidth())
                    && (x0 + x > 0)
                    && (y0 - y < g.getHeight())
                    && (y0 - y > 0)) {
                g.setRGB(x0 + x, y0 - y, Color.BLUE.getRGB());
            }
        }

        /**
         * Calculates screen borders.
         * Paint Grid & Function
         *
         * @param g - graphics
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            maxX = ((getSize().getWidth() - x0) / unitsX);
            minX = -(double) x0 / unitsX;
            minY = (-(getSize().getHeight() - y0) / unitsY);
            maxY = (double) y0 / unitsY;


            canvas = new BufferedImage((int) getSize().getWidth(), (int) getSize().getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = (Graphics2D) canvas.getGraphics();

            // Clear the background with white

            g2d.setBackground(Color.WHITE);
            g2d.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());


            paintGrid(canvas.getGraphics());
            canvas.getGraphics().setColor(BLUE);

            Lemniscate lemniscate = new Lemniscate(1);
            lemniscate.paint(canvas);


            g.drawImage(canvas, 0, 0, null);


        }

        /**
         * Paint grid starting from the origin (x0, y0) with steps unitX, unitY
         *
         * @param g - graphics
         */
        private void paintGrid(Graphics g) {
            Dimension size = getSize();

            g.setColor(Color.LIGHT_GRAY);
            int startX = x0;
            for (int x = startX; x < size.getWidth(); x += unitsX) {
                g.drawLine(x, 0, x, (int) size.getHeight());
            }
            for (int x = startX; x > 0; x -= unitsX) {
                g.drawLine(x, 0, x, (int) size.getHeight());
            }

            int startY = y0;
            for (int y = startY; y < size.getHeight(); y += unitsY) {
                g.drawLine(0, y, (int) size.getWidth(), y);
            }
            for (int y = startY; y > 0; y -= unitsY) {
                g.drawLine(0, y, (int) size.getWidth(), y);
            }

            g.setColor(Color.BLACK);
            for (int y = 0; y < (int) size.getHeight(); y++) {
                g.fillRect(x0, y, 1, 1);
            }
            for (int x = 0; x < (int) size.getWidth(); x++) {
                g.fillRect(x, y0, 1, 1);
            }
        }

        /**
         * if b == true add MouseWheelListener that knows how to scale right.
         *
         * @param b - boolean flag
         */
        public void turnScaleByWheel(boolean b) {
            if (b) {
                addMouseWheelListener(scale);
            } else {
                removeMouseWheelListener(scale);
            }
        }

        /**
         * if b == true add MouseListener that knows how to drag'n'drop right.
         *
         * @param b - boolean flag
         */
        public void turnDragAndDrop(boolean b) {
            if (b) {
                addMouseListener(getPoint);
                addMouseMotionListener(dragAndDropAxis);
            } else {
                removeMouseListener(getPoint);
                removeMouseMotionListener(dragAndDropAxis);
            }
        }

        /**
         * Lemniscate of Bernoulli
         */
        private class Lemniscate {
            private double c;
            private Point prevPoint;

            public Lemniscate(double c) {
                this.c = c;
            }

            /**
             * error function for Bresenham approach
             *
             * @param _x - X coord
             * @param _y - Y coord
             * @return
             */
            private double mistake(double _x, double _y) {
                double x = _x / unitsX;
                double y = _y / unitsY;

                return Math.abs((x * x + y * y) * (x * x + y * y) - 2 * c * c * (x * x - y * y));
            }

            /**
             * Due to drawing only 1 quadrant
             * helper function provided to check that pixel is in 1 quardrant
             *
             * @param x - - x coord
             * @param y - - y coord
             * @return
             */
            boolean isInFirstQuadrant(int x, int y) {
                return x >= 0 && y >= 0;
            }

            /**
             * drawing logic
             * using generalization of Bresenham
             *
             * @param g
             */
            public void paint(BufferedImage g) {
                Point point = new Point(0, 0);
                prevPoint = point;

                for (int i = 0; ; i++) {
                    Point nextPoint = nextToPaint(point, g);

                    putPixel((int) nextPoint.getX(), (int) nextPoint.getY(), g);
                    putPixel((int) -nextPoint.getX(), (int) nextPoint.getY(), g);
                    putPixel((int) nextPoint.getX(), (int) -nextPoint.getY(), g);
                    putPixel((int) -nextPoint.getX(), (int) -nextPoint.getY(), g);

                    prevPoint = point;
                    point = nextPoint;

                    if (point.getY() == 0) {
                        break;
                    }
                }
            }

            /**
             * find next point to put pixel in
             * should choose neighbor pixel with the smallest mistake
             *
             * @param point - current pixel
             * @param g     - canvas
             * @return - neighbor with the smallest mistake
             */
            private Point nextToPaint(Point point, BufferedImage g) {

                int x = (int) point.getX();
                int y = (int) point.getY();

                Point nextPoint = new Point((int) point.getX(), (int) point.getY() - 1);
                double minMistake = mistake((int) nextPoint.getX(), (int) nextPoint.getY());

                for (int i = 0; i < 2; i++) {

                    for (int j = -1; j < 2; j++) {
                        if (i == 0 && j == 0) {
                            continue;
                        }


                        if (x + i == prevPoint.getX()
                                && y + j == prevPoint.getY()) {
                            continue;
                        }

                        double m = mistake(x + i, y + j);
                        if (minMistake > m
                                && isInFirstQuadrant(x + i, y + j)
                                ) {
                            nextPoint = new Point(x + i, y + j);
                            minMistake = m;
                        }
                    }
                }
                return nextPoint;
            }
        }


        public int getScaleStep() {
            return scaleStep;
        }

        public void setScaleStep(int scaleStep) {
            this.scaleStep = scaleStep;
        }

        public int getMoveStep() {
            return moveStep;
        }

        public void setMoveStep(int moveStep) {
            this.moveStep = moveStep;
        }

        public int getX0() {
            return x0;
        }

        public int getY0() {
            return y0;
        }

        public int getUnitsX() {
            return unitsX;
        }

        public void setUnitsX(int unitsX) {
            this.unitsX = unitsX;
            repaint();
        }

        public int getUnitsY() {
            return unitsY;
        }

        public void setUnitsY(int unitsY) {
            this.unitsY = unitsY;
            repaint();
        }

        public void setX0(int x0) {
            this.x0 = x0;
            repaint();
        }

        public void setY0(int y0) {
            this.y0 = y0;
            repaint();
        }
    }

    public static void main(final String... args) {
        new ParametrizedFunction();
    }
}
