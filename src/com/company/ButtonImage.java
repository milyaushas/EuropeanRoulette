package com.company;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ButtonImage {
    private static Game game;
    public static void main(String[] args) {
        game = new Game();
        new ButtonImage();
    }

    public ButtonImage() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                JFrame frame = new JFrame("Game");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.add(new MenuPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

            }
        });
    }

    public static class MenuPane extends JPanel {

        public static final Rectangle NEW_GAME_BOUNDS = new Rectangle(260, 110, 262, 85);
        public static final Rectangle SETTINGS_BOUNDS = new Rectangle(260, 190, 262, 85);
        public static final Rectangle EXIT_GAME_BOUNDS = new Rectangle(260, 260, 262, 85);
        private BufferedImage img;
        private Rectangle selectedBounds;

        public MenuPane() {
            try {
                img = ImageIO.read(getClass().getResource("./background.jpg"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            MouseAdapter mouseHandler = new MouseAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    if (getNewGameBounds().contains(e.getPoint())) {
                        System.out.println("in new");
                        selectedBounds = getNewGameBounds();
                    } else if (getSettingsGameBounds().contains(e.getPoint())) {
                        System.out.println("in settings");
                        selectedBounds = getSettingsGameBounds();
                    }else if (getExitGameBounds().contains(e.getPoint())) {
                        System.out.println("in exit");
                        selectedBounds = getExitGameBounds();
                    } else {
                        selectedBounds = null;
                    }
                    repaint();
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    if (getNewGameBounds().contains(e.getPoint())) {
                        System.out.println("New Game");
                        new WindowGame();
                        try {
                            game.start();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    } else if (getSettingsGameBounds().contains(e.getPoint())) {
                        System.out.println("Settings Game");
                        new Settings();
                    }else if (getExitGameBounds().contains(e.getPoint())) {
                        System.out.println("Exit Game");
                        System.exit(0);
                    }
                }
            };
            addMouseListener(mouseHandler);
            addMouseMotionListener(mouseHandler);
        }

        @Override
        public Dimension getPreferredSize() {
            return img == null ? super.getPreferredSize() : new Dimension(img.getWidth(), img.getHeight());
        }

        protected Point getImageOffset() {

            Point p = new Point();
            if (img != null) {
                p.x = (getWidth() - img.getWidth()) / 2;
                p.y = (getHeight() - img.getHeight()) / 2;
            }

            return p;

        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (img != null) {
                Graphics2D g2d = (Graphics2D) g.create();

                Point p = getImageOffset();

                g2d.drawImage(img, p.x, p.y, this);

                drawText(g2d, "New Game", getNewGameBounds());
                drawText(g2d, "Settings Game", getSettingsGameBounds());
                drawText(g2d, "Exit Game", getExitGameBounds());

                g2d.dispose();
            }
        }

        protected void drawText(Graphics2D g2d, String text, Rectangle bounds) {

            FontMetrics fm = g2d.getFontMetrics();

            g2d.setColor(Color.GRAY);
            if (selectedBounds != null) {
                if (bounds.contains(selectedBounds)) {
                    RadialGradientPaint rpg = new RadialGradientPaint(
                            new Point(bounds.x + (bounds.width / 2), bounds.y + (bounds.height / 2)),
                            Math.min(bounds.width, bounds.height),
                            new float[]{0f, 1f},
                            new Color[]{new Color(252, 180, 42), new Color(97, 205, 181)}
                    );
                    g2d.setPaint(rpg);
                    RoundRectangle2D fill = new RoundRectangle2D.Float(bounds.x, bounds.y, bounds.width, bounds.height, 22, 22);
                    g2d.fill(fill);
                    g2d.setColor(Color.WHITE);
                }
            }

            g2d.drawString(
                    text,
                    bounds.x + ((bounds.width - fm.stringWidth(text)) / 2),
                    bounds.y + ((bounds.height - fm.getHeight()) / 2) + fm.getAscent());

        }

        protected Rectangle getNewGameBounds() {
            return getButtonBounds(NEW_GAME_BOUNDS);
        }

        protected Rectangle getSettingsGameBounds() {
            return getButtonBounds(SETTINGS_BOUNDS);
        }

        protected Rectangle getExitGameBounds() {
            return getButtonBounds(EXIT_GAME_BOUNDS);
        }

        protected Rectangle getButtonBounds(Rectangle masterBounds) {
            Rectangle bounds = new Rectangle(masterBounds);
            Point p = getImageOffset();
            bounds.translate(p.x, p.y);
            return bounds;
        }
    }

}


class Settings {
    public Settings() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                JFrame frame = new JFrame("Setting");
                frame.setLayout(new BorderLayout());
                frame.add(new MenuPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

            }
        });
    }

    public static class MenuPane extends JPanel {

        public static final Rectangle CHOICE = new Rectangle(100, 30, 100, 50);
        public static final Rectangle COUNT_1 = new Rectangle(130, 70, 50, 50);
        public static final Rectangle COUNT_2 = new Rectangle(130, 100, 50, 50);
        public static final Rectangle COUNT_3 = new Rectangle(130, 130, 50, 50);
        public static final Rectangle COUNT_4 = new Rectangle(130, 160, 50, 50);
        public static final Rectangle COUNT_5 = new Rectangle(130, 190, 50, 50);
        public static final Rectangle COUNT_6 = new Rectangle(130, 220, 50, 50);
        private BufferedImage img;
        private Rectangle selectedBounds;

        public MenuPane() {
            try {
                img = ImageIO.read(getClass().getResource("./background2.jpg"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            MouseAdapter mouseHandler = new MouseAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    if (getCount1().contains(e.getPoint())) {
                        System.out.println("count 1");
                        selectedBounds = getCount1();
                    } else if (getCount2().contains(e.getPoint())) {
                        System.out.println("count 2");
                        selectedBounds = getCount2();
                    } else if (getCount3().contains(e.getPoint())) {
                        System.out.println("count 3");
                        selectedBounds = getCount3();
                    } else if (getCount4().contains(e.getPoint())) {
                        System.out.println("count 4");
                        selectedBounds = getCount4();
                    } else if (getCount5().contains(e.getPoint())) {
                        System.out.println("count 5");
                        selectedBounds = getCount5();
                    } else if (getCount6().contains(e.getPoint())) {
                        System.out.println("count 6");
                        selectedBounds = getCount6();
                    } else {
                        selectedBounds = null;
                    }
                    repaint();
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    if (getCount1().contains(e.getPoint())) {
                        System.out.println("Count1");
                    } else if (getCount2().contains(e.getPoint())) {
                        System.out.println("Count2");
                    } else if (getCount3().contains(e.getPoint())) {
                        System.out.println("Count3");
                    } else if (getCount4().contains(e.getPoint())) {
                        System.out.println("Count4");
                    } else if (getCount5().contains(e.getPoint())) {
                        System.out.println("Count5");
                    } else if (getCount6().contains(e.getPoint())) {
                        System.out.println("Count6");
                    }
                }
            };
            addMouseListener(mouseHandler);
            addMouseMotionListener(mouseHandler);
        }

        @Override
        public Dimension getPreferredSize() {
            return img == null ? super.getPreferredSize() : new Dimension(img.getWidth(), img.getHeight());
        }

        protected Point getImageOffset() {

            Point p = new Point();
            if (img != null) {
                p.x = (getWidth() - img.getWidth()) / 2;
                p.y = (getHeight() - img.getHeight()) / 2;
            }

            return p;

        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (img != null) {
                Graphics2D g2d = (Graphics2D) g.create();

                Point p = getImageOffset();

                g2d.drawImage(img, p.x, p.y, this);

                drawText(g2d, "Выберите количество игроков", CHOICE);
                drawText(g2d, "1", getCount1());
                drawText(g2d, "2", getCount2());
                drawText(g2d, "3", getCount3());
                drawText(g2d, "4", getCount4());
                drawText(g2d, "5", getCount5());
                drawText(g2d, "6", getCount6());

                g2d.dispose();
            }
        }

        protected void drawText(Graphics2D g2d, String text, Rectangle bounds) {

            FontMetrics fm = g2d.getFontMetrics();

            g2d.setColor(Color.GRAY);
            if (selectedBounds != null) {
                if (bounds.contains(selectedBounds)) {
                    RadialGradientPaint rpg = new RadialGradientPaint(
                            new Point(bounds.x + (bounds.width / 2), bounds.y + (bounds.height / 2)),
                            Math.min(bounds.width, bounds.height),
                            new float[]{0f, 1f},
                            new Color[]{new Color(252, 180, 42), new Color(97, 205, 181)}
                    );
                    g2d.setPaint(rpg);
                    RoundRectangle2D fill = new RoundRectangle2D.Float(bounds.x, bounds.y, bounds.width, bounds.height, 22, 22);
                    g2d.fill(fill);
                    g2d.setColor(Color.BLACK);
                }
            }

            g2d.drawString(
                    text,
                    bounds.x + ((bounds.width - fm.stringWidth(text)) / 2),
                    bounds.y + ((bounds.height - fm.getHeight()) / 2) + fm.getAscent());

        }

        protected Rectangle getCount1() {
            return getButtonBounds(COUNT_1);
        }
        protected Rectangle getCount2() { return getButtonBounds(COUNT_2);}
        protected Rectangle getCount3() {
            return getButtonBounds(COUNT_3);
        }
        protected Rectangle getCount4() {
            return getButtonBounds(COUNT_4);
        }
        protected Rectangle getCount5() {
            return getButtonBounds(COUNT_5);
        }
        protected Rectangle getCount6() {
            return getButtonBounds(COUNT_6);
        }


        protected Rectangle getButtonBounds(Rectangle masterBounds) {
            Rectangle bounds = new Rectangle(masterBounds);
            Point p = getImageOffset();
            bounds.translate(p.x, p.y);
            return bounds;
        }
    }

}


class WindowGame {
    public WindowGame() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                JFrame frame = new JFrame("Game");
                frame.setLayout(new BorderLayout());
                frame.add(new MenuPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

            }
        });
    }

    public static class MenuPane extends JPanel {
        private BufferedImage img;
        private Rectangle selectedBounds;

        public MenuPane() {
            try {
                img = ImageIO.read(getClass().getResource("./background.jpg"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            MouseAdapter mouseHandler = new MouseAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    selectedBounds = null;
                    repaint();
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                }
            };
            addMouseListener(mouseHandler);
            addMouseMotionListener(mouseHandler);
        }

        @Override
        public Dimension getPreferredSize() {
            return img == null ? super.getPreferredSize() : new Dimension(img.getWidth(), img.getHeight());
        }

        protected Point getImageOffset() {

            Point p = new Point();
            if (img != null) {
                p.x = (getWidth() - img.getWidth()) / 2;
                p.y = (getHeight() - img.getHeight()) / 2;
            }

            return p;

        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (img != null) {
                Graphics2D g2d = (Graphics2D) g.create();

                Point p = getImageOffset();

                g2d.drawImage(img, p.x, p.y, this);
                g2d.dispose();
            }
        }
    }

}