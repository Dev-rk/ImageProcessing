        package ImageProcessing;

        import com.sun.prism.*;

        import javax.imageio.ImageIO;
        import javax.swing.*;
        import java.awt.*;
        import java.awt.Graphics;
        import java.awt.event.*;
        import java.awt.geom.AffineTransform;
        import java.awt.image.BufferedImage;
        import java.awt.image.ColorModel;
        import java.awt.image.RescaleOp;
        import java.awt.image.WritableRaster;
        import java.io.File;
        import java.io.IOException;
        import java.lang.reflect.Array;
        import java.util.Arrays;

/**
 * Created by Roma on 28.09.2015.
 */
public class imageProcessingGUI extends Thread {

    public String frameName = "Image processing";

    public static Thread thread = null;
    public static JFrame frame;
    public static JPanel panel;
    public static File inputFile;
    public static BufferedImage bufferedImageOriginal = null;
    public static BufferedImage bufferedImageWorked = null;
    public static BufferedImage bufferedImageProcessed = null;
    public static BufferedImage bufferedImageProcessedPart = null;
    public static BufferedImage bufferedImageCopy = null;
    public static BufferedImage bufferedImageCrop = null;
    public static Rectangle rectangleCap = null;
    public static Point startPoint = null;
    public static Dimension dimensionImage = null;

    public float radian;

    public boolean editMove = false;
    public boolean editSelected = false;

    imageProcessingGUI() {
    }

    public void generateGui() {
        generateMainMenu();
        frame.setVisible(true);
        frame.revalidate();
        frame.repaint();
    }

    public void generateMainMenu() {
        panel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        panel.setLayout(layout);
        c.anchor = GridBagConstraints.NORTHWEST;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(2, 2, 2, 2);
        JLabel imagePlace = new JLabel("");
        layout.setConstraints(imagePlace, c);
        JLabel imagePosition = new JLabel("");
        c.gridy = 1;
        layout.setConstraints(imagePosition, c);
        JFrame newFrame = new JFrame(frameName);
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem openFileItem = new JMenuItem("Open image");
        JMenuItem saveAsFileItem = new JMenuItem("Save as...");
        JMenuItem exitFileItem = new JMenuItem("Exit");

        fileMenu.add(openFileItem);
        fileMenu.add(saveAsFileItem);
        fileMenu.addSeparator();
        fileMenu.add(exitFileItem);

// JMenu editMenu = new JMenu("Edit");
// JMenuItem selectEditItem = new JMenuItem("Select area");
// JMenuItem cropEditItem = new JMenuItem("Crop selected");
// JMenuItem moveEditItem = new JMenuItem("Move selected");
// JMenuItem changeSizeEditItem = new JMenuItem("Change size");
// JMenuItem rotateEditItem = new JMenuItem("Rotate image");
// JMenuItem resetEditItem = new JMenuItem("Discard all changes");

        JMenu filterMenu = new JMenu("Filter");
// JMenu noiseFilterMenu = new JMenu("Make noise");
// JMenuItem saltpepperNoiseFilterMenu = new JMenuItem("Salt-Pepper");

        JMenu filteringFilterMenu = new JMenu("Filters");
        JMenuItem medianFilteringFilterMenu = new JMenuItem("Median");
        JMenuItem medianFiltering1FilterMenu = new JMenuItem("Median1");
        JMenuItem medianFiltering2FilterMenu = new JMenuItem("Median2");
        JMenuItem linearFilteringFilterMenu = new JMenuItem("Linear contrast");
// JMenuItem impositionFilteringFilterMenu = new JMenuItem("Imposition");
        JMenuItem geneticFilteringFilterMenu = new JMenuItem("Genetic");

// noiseFilterMenu.add(saltpepperNoiseFilterMenu);
        filteringFilterMenu.add(medianFilteringFilterMenu);
        filteringFilterMenu.add(medianFiltering1FilterMenu);
        filteringFilterMenu.add(medianFiltering2FilterMenu);
        filteringFilterMenu.add(linearFilteringFilterMenu);
// filteringFilterMenu.add(impositionFilteringFilterMenu);
        filteringFilterMenu.add(geneticFilteringFilterMenu);

// filterMenu.add(noiseFilterMenu);
        filterMenu.add(filteringFilterMenu);

// editMenu.add(selectEditItem);
// editMenu.add(cropEditItem);
// editMenu.add(moveEditItem);
// editMenu.add(changeSizeEditItem);
//
//        editMenu.add(rotateEditItem);
// editMenu.add(resetEditItem);

        menuBar.add(fileMenu);
// menuBar.add(editMenu);
        menuBar.add(filterMenu);

// editMenu.setEnabled(false);
        filterMenu.setEnabled(false);

        panel.add(imagePlace);
        panel.add(imagePosition);

        newFrame.add(panel);

        newFrame.setJMenuBar(menuBar);
        newFrame.setPreferredSize(new Dimension(700, 500));
        newFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame = newFrame;
        frame.pack();
/*****************************************************************************/
/*****************************************************************************/
        MouseListener mouseListener_select = new MouseListener() {
            Point point = new Point();

            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("mouseClicked: " + e.getPoint());
// if (!editMove) {
                imagePlace.setIcon(new ImageIcon(bufferedImageWorked));
// imagePlace.setIcon(new ImageIcon(bufferedImageOriginal));
                imagePlace.repaint();
// } else {
// bufferedImageWorked = bufferedImageProcessed;
// editMove=false;
// }
                if (editSelected){
                    editSelected = false;
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("mousePressed: " + e.getPoint());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("mouseReleased: " + e.getPoint());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                System.out.println("mouseEntered: " + e.getPoint());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                System.out.println("mouseExited: " + e.getPoint());
            }
        };

/**************************************************************************************/

        MouseMotionListener mouseMotionListener_select = new MouseMotionListener() {

            Point start = new Point();
            Point end = new Point();

            @Override
            public void mouseDragged(MouseEvent e) {

                end = e.getPoint();

                if (end.x < 0) {
                    end.x = 0;
                } else if (end.x > bufferedImageWorked.getWidth()) {
                    end.x = bufferedImageWorked.getWidth();
                }
                if (end.y < 0) {
                    end.y = 0;
                } else if (end.y > bufferedImageWorked.getHeight()) {
                    end.y = bufferedImageWorked.getHeight();
                }
                System.out.println(end);
                int xStart = Math.min(start.x, end.x);
                int yStart = Math.min(start.y, end.y);
                int widthRect = Math.abs(end.x - start.x);
                int heightRect = Math.abs(end.y - start.y);

                startPoint = new Point(xStart, yStart);
                dimensionImage = new Dimension(widthRect, heightRect);
                rectangleCap = new Rectangle(new Point(xStart, yStart), new Dimension(widthRect, heightRect));
                Graphics2D drawed = (Graphics2D) bufferedImageCopy.createGraphics();
                drawed.drawImage(bufferedImageWorked, 0, 0, null);
                drawed.setColor(Color.blue);
                drawed.draw(rectangleCap);
                drawed.setColor(new Color(255, 255, 255, 15));
                drawed.fill(rectangleCap);
                drawed.dispose();
                imagePlace.setIcon(new ImageIcon(bufferedImageCopy));
                imagePlace.repaint();

                imagePosition.setText("Width: " + Math.abs(end.x - start.x) + " Height: " + Math.abs(end.y - start.y));
                imagePosition.repaint();

            }

            @Override
            public void mouseMoved(MouseEvent e) {
// if (editMove) {
// bufferedImageWorked = bufferedImageProcessed;
// editMove=false;
// }
                start = e.getPoint();
                imagePosition.setText("Position moved: X=" + e.getX() + " Y=" + e.getY());
                imagePosition.repaint();
            }
        };
/******************************************************************************************************/
        MouseListener mouseListener_move = new MouseListener() {
            Point point = new Point();

            @Override
            public void mouseClicked(MouseEvent e) {
// System.out.println("mouseClicked: " + e.getPoint());
// if (!editMove) {
// imagePlace.setIcon(new ImageIcon(bufferedImageWorked));
// imagePlace.repaint();
// } else { editMove=false; }
// if (editSelected){
// editSelected = false;
// }
            }

            @Override
            public void mousePressed(MouseEvent e) {
// System.out.println("mousePressed: " + e.getPoint());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("mouseReleased: " +
                        e.getPoint());
// bufferedImageProcessed = bufferedImageCopy;
                editMove = true;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                System.out.println("mouseEntered: " + e.getPoint());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                System.out.println("mouseExited: " + e.getPoint());
            }
        };

/******************************************************************************************************/
        MouseMotionListener mouseMotionListener_move = new MouseMotionListener() {
            Point startMove = new Point();
            Point endMove = new Point();

            @Override
            public void mouseDragged(MouseEvent e) {
                if (dimensionImage.width > 0 && dimensionImage.height > 0) {
                    BufferedImage crop = bufferedImageWorked.getSubimage(
                            startPoint.x,
                            startPoint.y,
                            dimensionImage.width,
                            dimensionImage.height);

                    Graphics2D g = bufferedImageCopy.createGraphics();
                    g.drawImage(bufferedImageWorked, 0, 0, null);
                    g.drawImage(crop, e.getX()-(dimensionImage.width/2), e.getY()-(+dimensionImage.height/2), null);
                    g.dispose();
                    imagePlace.setIcon(new ImageIcon(bufferedImageCopy));
                    imagePlace.repaint();
                    editMove = true;
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {}
        };
/******************************************************************************************************/

        openFileItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bufferedImageWorked = openFile();
                bufferedImageOriginal = bufferedImageWorked;
                bufferedImageCopy = new BufferedImage(
                        bufferedImageWorked.getWidth(),
                        bufferedImageWorked.getHeight(),
                        bufferedImageWorked.getType()
                );
                bufferedImageProcessed = bufferedImageOriginal;

                System.out.println(frame.getPreferredSize());

                imagePlace.setIcon(new ImageIcon(bufferedImageWorked));
                imagePlace.repaint();
// editMenu.setEnabled(true);
                filterMenu.setEnabled(true);
            }
        });

        saveAsFileItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Specify a file to save");

                int userSelection = fileChooser.showSaveDialog(frame);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    try {
                        ImageIO.write(bufferedImageProcessed, "bmp", fileToSave);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
                }

            }
        });

        exitFileItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

// selectEditItem.addActionListener(new ActionListener() {
// @Override
// public void actionPerformed(ActionEvent e) {
//
//// bufferedImageWorked = bufferedIswaqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq
//// bufferedImageWorked = bufferedImageProcessed;
//// editMove = false;
//// }
// imagePlace.removeMouseMotionListener(mouseMotionListener_move);
// imagePlace.removeMouseListener(mouseListener_move);
//
// imagePlace.addMouseMotionListener(mouseMotionListener_select);
// imagePlace.addMouseListener(mouseListener_select);
// }
// });

// cropEditItem.addActionListener(new ActionListener() {
// @Override
// public void actionPerformed(ActionEvent e) {
// if (dimensionImage.width > 0 && dimensionImage.height > 0) {
// imagePlace.removeMouseMotionListener(mouseMotionListener_select);
// imagePlace.removeMouseListener(mouseListener_select);
// imagePlace.removeMouseListener(mouseListener_move);
// imagePlace.removeMouseMotionListener(mouseMotionListener_move);
//
// bufferedImageCrop = bufferedImageWorked.getSubimage(startPoint.x, startPoint.y, dimensionImage.width, dimensionImage.height);
// bufferedImageProcessed = bufferedImageCrop;
// imagePlace.setIcon(new ImageIcon(bufferedImageCrop));
// imagePlace.repaint();
// }
// }
// });

// moveEditItem.addActionListener(new ActionListener() {
// @Override
// public void actionPerformed(ActionEvent e) {
// if
//        (dimensionImage.width > 0 && dimensionImage.height > 0) {
//
// imagePlace.removeMouseMotionListener(mouseMotionListener_select);
// imagePlace.removeMouseListener(mouseListener_select);
//
// bufferedImageWorked = bufferedImageProcessed;
//
// imagePlace.addMouseMotionListener(mouseMotionListener_move);
// imagePlace.addMouseListener(mouseListener_move);
// }
//
// }
// });

// changeSizeEditItem.addActionListener(new ActionListener() {
// @Override
// public void actionPerformed(ActionEvent e) {
// imagePlace.removeMouseMotionListener(mouseMotionListener_select);
// imagePlace.removeMouseListener(mouseListener_select);
//
// imagePlace.removeMouseMotionListener(mouseMotionListener_move);
// imagePlace.removeMouseListener(mouseListener_move);
//
// ResizeDialog resizeDialog = new ResizeDialog();
// Dimension newDimension = new Dimension();
// newDimension = resizeDialog.showMessage("Resize", "Please, enter new image size.");
// System.out.println(newDimension);
// if (newDimension.height>0 && newDimension.width>0){
// BufferedImage resized;
// resized = new BufferedImage(newDimension.width, newDimension.height, bufferedImageWorked.getType() );
// Graphics2D gr = resized.createGraphics();
// gr.drawImage(bufferedImageOriginal, 0, 0, newDimension.width, newDimension.height, null);
// imagePlace.setIcon(new ImageIcon(resized));
// imagePlace.repaint();
// gr.dispose();
// bufferedImageProcessed = resized;
// }
// }
// });
//
// rotateEditItem.addActionListener(new ActionListener() {
// @Override
// public void actionPerformed(ActionEvent e) {
// imagePlace.removeMouseMotionListener(mouseMotionListener_select);
// imagePlace.removeMouseListener(mouseListener_select);
// imagePlace.removeMouseMotionListener(mouseMotionListener_move);
// imagePlace.removeMouseListener(mouseListener_move);
//
// bufferedImageProcessed = makeImageRotate(bufferedImageWorked, bufferedImageOriginal.getWidth(), bufferedImageOriginal.getHeight());
//
// imagePlace.setIcon(new ImageIcon(bufferedImageProcessed));
// imagePlace.repaint();
// }
// });

// resetEditItem.addActionListener(new ActionListener() {
// @Override
// public void actionPerformed(ActionEvent e) {
// bufferedImageWorked = bufferedImageOriginal;
// bufferedImageProcessed = bufferedImageOriginal;
// bufferedImageCopy = new BufferedImage(
// bufferedImageOriginal.getWidth(),
// bufferedImageOriginal.getHeight(),
// bufferedImageOriginal.getType()
// );
//
// rectangleCap = null;
// startPoint = new Point();
// dimensionImage = new Dimension();
// imagePlace.removeMouseMotionListener(mouseMotionListener_move);
// imagePlace.removeMouseMotionListener(mouseMotionListener_select);
// imagePlace.removeMouseListener(mouseListener_move);
// imagePlace.removeMouseListener(mouseListener_select);
// imagePlace.setIcon(new ImageIcon(bufferedImageOriginal));
// imagePlace.repaint();
// }
// });

            medianFilteringFilterMenu.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    BufferedImage medianFilterImage = bufferedImageOriginal;
                    long[] arrPixel = new long[9];
                    for (int x=1; x<medianFilterImage.getWidth(); x++){
                        for (int y=1; y<medianFilterImage.getHeight(); y++){
                            if(y+1<medianFilterImage.getHeight() && (x+1<medianFilterImage.getWidth())){
                                arrPixel[0] = medianFilterImage.getRGB(x - 1, y - 1);
                                arrPixel[1] = medianFilterImage.getRGB(x, y - 1);
                                arrPixel[2] = medianFilterImage.getRGB(x + 1, y - 1);
                                arrPixel[3] = medianFilterImage.getRGB(x - 1, y);
                                arrPixel[4] = medianFilterImage.getRGB(x, y);
                                arrPixel[5] = medianFilterImage.getRGB(x + 1, y);
                                arrPixel[6] = medianFilterImage.getRGB(x - 1, y + 1);
                                arrPixel[7] = medianFilterImage.getRGB(x, y + 1);
                                arrPixel[8] = medianFilterImage.getRGB(x + 1, y + 1);
                            }
                            Arrays.sort(arrPixel);
                            medianFilterImage.setRGB(x,y,(int) arrPixel[4]);
                        }
                    }
                    imagePlace.setIcon(new ImageIcon(medianFilterImage));
                    imagePlace.repaint();
                }
            });

            medianFiltering1FilterMenu.addActionListener(new
                                                                 ActionListener() {
                                                                     @Override
                                                                     public void actionPerformed(ActionEvent e) {
                                                                         BufferedImage medianFilterImage = bufferedImageOriginal;
                                                                         long[] arrPixel = new long[8];
                                                                         for (int x=1; x<medianFilterImage.getWidth(); x++){
                                                                             for (int y=1; y<medianFilterImage.getHeight(); y++){
                                                                                 if(y+1<medianFilterImage.getHeight() && (x+1<medianFilterImage.getWidth())){
                                                                                     arrPixel[0] = medianFilterImage.getRGB(x - 1, y - 1);
                                                                                     arrPixel[1] = medianFilterImage.getRGB(x, y - 1);
                                                                                     arrPixel[2] = medianFilterImage.getRGB(x + 1, y - 1);
                                                                                     arrPixel[3] = medianFilterImage.getRGB(x - 1, y);
// arrPixel[4] = medianFilterImage.getRGB(x, y);
                                                                                     arrPixel[4] = medianFilterImage.getRGB(x + 1, y);
                                                                                     arrPixel[5] = medianFilterImage.getRGB(x - 1, y + 1);
                                                                                     arrPixel[6] = medianFilterImage.getRGB(x, y + 1);
                                                                                     arrPixel[7] = medianFilterImage.getRGB(x + 1, y + 1);
                                                                                 }
                                                                                 Arrays.sort(arrPixel);
                                                                                 medianFilterImage.setRGB(x,y,(int) arrPixel[4]);
                                                                             }
                                                                         }
                                                                         imagePlace.setIcon(new ImageIcon(medianFilterImage));
                                                                         imagePlace.repaint();
                                                                     }
                                                                 });

            medianFiltering2FilterMenu.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    BufferedImage medianFilterImage = bufferedImageOriginal;
                    long[] arrPixel = new long[9];
                    long i = 0;
                    for (int x=1; x<medianFilterImage.getWidth(); x++){
                        for (int y=1; y<medianFilterImage.getHeight(); y++){
                            if(y+1<medianFilterImage.getHeight() && (x+1<medianFilterImage.getWidth())){
                                arrPixel[0] = medianFilterImage.getRGB(x - 1, y - 1);
                                arrPixel[1] = medianFilterImage.getRGB(x, y - 1);
                                arrPixel[2] = medianFilterImage.getRGB(x + 1, y - 1);
                                arrPixel[3] = medianFilterImage.getRGB(x - 1, y);
                                arrPixel[4] = medianFilterImage.getRGB(x, y);
                                arrPixel[5] = medianFilterImage.getRGB(x + 1, y);
                                arrPixel[6] = medianFilterImage.getRGB(x - 1, y + 1);
                                arrPixel[7] = medianFilterImage.getRGB(x, y + 1);
                                arrPixel[8] = medianFilterImage.getRGB(x + 1, y + 1);
                                i = (arrPixel[0] + arrPixel[1] + arrPixel[2] + arrPixel[3] + arrPixel[5] + arrPixel[6] + arrPixel[7] + arrPixel[8])/8;
                                if (i-arrPixel[4] <180000 && i-arrPixel[4] >-180000)
                                {
//                                    System.out.println(i-arrPixel[4]);

                                }
                                else {
//                                    System.out.println(i-arrPixel[4]);
                                    Arrays.sort(arrPixel);
                                    medianFilterImage.setRGB(x, y, (int) arrPixel[4]);
//                                    System.out.println("change pixel");
                                }
                            }

                        }
                    }
                    imagePlace.setIcon(new ImageIcon(medianFilterImage));
                    imagePlace.repaint();
                }
            });

            linearFilteringFilterMenu.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    BufferedImage medianFilterImage = bufferedImageOriginal;

                    RescaleOp rescale;
                    rescale = new RescaleOp(.8f, 10.0f, null);
                    medianFilterImage = rescale.filter(medianFilterImage, null);
                    imagePlace.setIcon(new ImageIcon(medianFilterImage));
                    imagePlace.repaint();
                }
            });

            geneticFilteringFilterMenu.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    BufferedImage medianFilterImage = bufferedImageOriginal;
                    Color color;
                    long[] arrPixel = new long[9];
                    long i = 0;
                    int red, green, blue;
                    long min = 0;
                    long max = -444444444;
                    for (int x = 1; x < medianFilterImage.getWidth(); x++) {
                        for (int y = 1; y < medianFilterImage.getHeight(); y++) {
                            if(y+1<medianFilterImage.getHeight() && (x+1<medianFilterImage.getWidth())){
                                arrPixel[0] = medianFilterImage.getRGB(x - 1, y - 1);
                                arrPixel[1] = medianFilterImage.getRGB(x, y - 1);
                                arrPixel[2] = medianFilterImage.getRGB(x + 1, y - 1);
                                arrPixel[3] = medianFilterImage.getRGB(x - 1, y);
                                arrPixel[4] = medianFilterImage.getRGB(x, y);
                                arrPixel[5] = medianFilterImage.getRGB(x + 1, y);
                                arrPixel[6] = medianFilterImage.getRGB(x - 1, y + 1);
                                arrPixel[7] = medianFilterImage.getRGB(x, y + 1);
                                arrPixel[8] = medianFilterImage.getRGB(x + 1, y + 1);

                                i = (arrPixel[0] + arrPixel[1] + arrPixel[2] + arrPixel[3] + arrPixel[5] + arrPixel[6] + arrPixel[7] + arrPixel[8])/8;
//                                System.out.println(i+arrPixel[4]);

                                if (i+arrPixel[4] >-20357786){
                                    Arrays.sort(arrPixel);
                                    medianFilterImage.setRGB(x, y, (int)
                                            arrPixel[4]);
                                }
                            }
                        }
                    }

                    imagePlace.setIcon(new ImageIcon(medianFilterImage));
                    imagePlace.repaint();
                }
            });

        }

    public BufferedImage openFile() {
        JFileChooser dialog = new JFileChooser();
        dialog.setFileFilter(new imageFileFilter());
        dialog.showOpenDialog(dialog);
        File file = dialog.getSelectedFile();

        try {
            BufferedImage img = ImageIO.read(file);
            return img;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateFrame() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.revalidate();
                frame.repaint();
            }
        });
    }

    public BufferedImage makeImageRotate(BufferedImage image,int w,int h){
        String input = JOptionPane.showInputDialog("Enter angle:");
        BufferedImage bi= new BufferedImage(w,h,bufferedImageWorked.getType());
        Graphics2D g2d=(Graphics2D)bi.createGraphics();
        radian = Float.parseFloat(input);
        g2d.translate(image.getWidth() / 2, image.getHeight() / 2); //move to coordinate (w/2,h/2)
        g2d.rotate(Math.toRadians(radian)); //rotate the image
        g2d.translate(-image.getWidth() / 2, -image.getHeight() / 2); //move the coordinate back
        g2d.drawImage(image, 0, 0, null); //draw the rotated image

        g2d.dispose();

        return bi;
    }
    public BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(bi.getRaster().createCompatibleWritableRaster());
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
}


