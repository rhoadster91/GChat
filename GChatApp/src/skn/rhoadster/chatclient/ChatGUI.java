package skn.rhoadster.chatclient;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.Utilities;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
 
public class ChatGUI extends JPanel implements ActionListener
{
	protected static String roomname;
	protected static String name;
	protected static Socket socket;	
	protected static ObjectInputStream objIn;
	protected static ObjectOutputStream objOut;
	protected static boolean isForeground = true;
	protected static HTMLEditorKit kit = new HTMLEditorKit();
	protected static HTMLDocument doc = new HTMLDocument();    
	protected static JFrame frame;
	private static final long serialVersionUID = -8049272721757193714L;
	protected static JTextField textField;
    protected static JTextPane textPane;    
    protected static JTextField typingField;
    protected static ArrayList<String> typingPeople = new ArrayList<String>();
    protected static int count = 0;
    protected static Thread titleNotifier;
    public ChatGUI() 
    {
        super(new GridBagLayout());
 
        textField = new JTextField(20);
        textField.addActionListener(this); 
        textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textPane);
        textPane.setBounds(0, 0, 20, 80);
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER; 
        c.fill = GridBagConstraints.HORIZONTAL;
        add(textField, c); 
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(scrollPane, c);
        c = new GridBagConstraints();        
        c.gridwidth = GridBagConstraints.REMAINDER;        
        c.fill = GridBagConstraints.HORIZONTAL;        
        typingField = new JTextField(20);   
        typingField.setEditable(false);
        add(typingField, c);
        textField.addKeyListener(new KeyListener()
        {

			@Override
			public void keyPressed(KeyEvent arg0) 
			{
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) 
			{
				if(!textField.getText().contentEquals(""))
				{
					Message msg = new Message();
					msg.content = name;
					msg.room = roomname;
					msg.sender = "person_is_typing";
					writeMessage(msg);					
				}
				else
				{
					Message msg = new Message();
					msg.content = name;
					msg.room = roomname;
					msg.sender = "person_is_not_typing";
					writeMessage(msg);
				}
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
        	
        });
    }
 
    public void actionPerformed(ActionEvent evt) 
    {
    	Message msg = new Message();
		msg.content = textField.getText();
		msg.room = roomname;
		msg.sender = name;
		writeMessage(msg);		
		textField.setText("");
		textField.selectAll();
    }
 
    private static void createAndShowGUI() 
    {
        frame = new JFrame("GChat - " + roomname + " room");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ChatGUI());
        frame.addWindowStateListener(new WindowStateListener()
        {

			@Override
			public void windowStateChanged(WindowEvent arg0) 
			{
				if(arg0.getNewState()==0)
				{
					isForeground = true;
					count = 0;
					frame.setTitle("GChat - " + roomname + " room");
				}
				else
				{
					isForeground = false;	
					
				}
			}
        	
        });
        frame.addWindowFocusListener(new WindowFocusListener()
        {

			@Override
			public void windowGainedFocus(WindowEvent arg0) 
			{
				isForeground = true;
				count = 0;
				frame.setTitle("GChat - " + roomname + " room");
			}

			@Override
			public void windowLostFocus(WindowEvent arg0)
			{
				isForeground = false;
				
			}
        	
        });
        frame.pack();
        frame.setBounds(20, 20, 300, 300);
        frame.setVisible(true);
        initListener();
		
		new Thread()
		{
			public void run()
			{
				Message msg;
				while(true)
				{
					try 
					{
						msg = (Message)objIn.readObject();
						if(msg.sender!=null && msg.sender.contentEquals("person_is_typing"))
						{
							boolean exists = false;
							if(!msg.content.contentEquals(name))
							{
								Iterator<String> tempStrIterator = typingPeople.iterator();
								while(tempStrIterator.hasNext())
								{
									String cur = tempStrIterator.next();
									if(cur.contentEquals(msg.content))
										exists = true;
								}
								if(!exists)
									typingPeople.add(msg.content);
								refreshTypers();
							}
						}
						else if(msg.sender!=null && msg.sender.contentEquals("person_is_not_typing"))
						{
							Iterator<String> tempStrIterator = typingPeople.iterator();
							while(tempStrIterator.hasNext())
							{
								String cur = tempStrIterator.next();
								if(cur.contentEquals(msg.content))
									tempStrIterator.remove();
							}
							refreshTypers();
						}
						else
						{
							if(msg.sender != null && msg.content != null)
							{
								String text = new String("<b>" + msg.sender + ":</b> " + msg.content + "<br>");					        
								textPane.setEditorKit(kit);
							    textPane.setDocument(doc);
							    try
								{
									kit.insertHTML(doc, doc.getLength(), text, 0, 0, null);
								}
								catch (BadLocationException e) 
								{
									e.printStackTrace();
								}
						        
						        if(!isForeground)
						        {
						        	count++;
						        	titleNotifier = new Thread()
						            {
						            	@Override
						            	public void run()
						            	{
						            		boolean titleShown = false;    		    			
						            		frame.setTitle("GChat - " + roomname + " room");
						            		while(!isForeground)
						            		{
						            			if(titleShown)
						            			{
						            				frame.setTitle("You have " + count + " new messages.");
						            			}
						            			else
						            			{
						            				frame.setTitle("GChat - " + roomname + " room");
						            			}
						            			titleShown = !titleShown;
						            			try
						            			{
						        					Thread.sleep(1000);
						        				} 
						            			catch (InterruptedException e) 
						            			{
						        					e.printStackTrace();
						        				}
						            		}
						            		
						            	}
						            	
						            };
						        	titleNotifier.start();
						        	frame.setTitle("You have " + count + " new messages.");
						        	
						        }				        
							}							
							else if(msg.sender == null && msg.content!=null)
							{
								String text = new String("<i>" + msg.content + "</i><br>");							
								textPane.setEditorKit(kit);
							    textPane.setDocument(doc);
							    try
								{
									kit.insertHTML(doc, doc.getLength(), text, 0, 0, null);
								}
								catch (BadLocationException e) 
								{
									e.printStackTrace();
								}					        
							}
						}
						textPane.setCaretPosition(doc.getLength());
					}
					catch (IOException e) 
					{
						ChatGUI.writeToScreen("<b><i><font color=\"RED\">Server problem. Please restart.</font></i><b>");
						break;
					}
					catch (ClassNotFoundException e) 
					{
						e.printStackTrace();
					}	
					catch(ClassCastException e)
					{
						e.printStackTrace();
					}
				}
			}
		}.start();
    }
 
    public static void launch() 
    {
    	roomname = ClientMain.roomname;
    	name = ClientMain.name;
    	socket = ClientMain.socket;
    	objIn = ClientMain.objIn;
    	objOut = ClientMain.objOut;
    	
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
            public void run() {
                createAndShowGUI();
            }
        });
        
    }
    
    public static void writeToScreen(String text)
    {    								
		textPane.setEditorKit(kit);
	    textPane.setDocument(doc);
	    try
		{
			kit.insertHTML(doc, doc.getLength(), text, 0, 0, null);
		}
		catch (BadLocationException e) 
		{
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		textPane.setCaretPosition(doc.getLength());
    }

	public static void refreshTypers()
	{
		int cur = 0;
		String typers= new String(""), temp = new String("");
		Iterator<String> nameIterator = typingPeople.iterator();
		if(!nameIterator.hasNext())
		{
			typingField.setText("");
			return;
		}
		while(nameIterator.hasNext())
		{
			if(cur>0 && cur == typingPeople.size() - 1)
			{
				typers = typers.concat(" and ");
			}
			else if(cur>0 && cur != typingPeople.size() - 1)
			{
				typers = typers.concat(", ");
			}			
			cur = cur + 1;
			temp = nameIterator.next();
			typers = typers.concat(temp);
		}
		if(typingPeople.size() > 1)
			typers = typers.concat(" are typing...");
		else
			typers = typers.concat(" is typing...");
		typingField.setText(typers);
	}
	
	private static synchronized void writeMessage(Message m)
	{
		try 
		{
			objOut.writeObject(m);
			objOut.flush();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
	}
	private static void initListener()
	{
        doc.addDocumentListener(new DocumentListener()
        {
            public void insertUpdate(DocumentEvent event) 
            {
                final DocumentEvent e=event;
                SwingUtilities.invokeLater(new Runnable() 
                {
                    public void run() 
                    {
                    	checkFor(":)", SmileyClass.SMILE_IMG);
                    	checkFor(":-)", SmileyClass.SMILE_IMG);
                    	checkFor(":(", SmileyClass.FROWN_IMG);
                    	checkFor(":-(", SmileyClass.FROWN_IMG);
                    	checkFor(":D", SmileyClass.LAUGH_IMG);
                    	checkFor(":-D", SmileyClass.LAUGH_IMG);
                    	checkFor(":P", SmileyClass.TONGUE_IMG);
                    	checkFor(":-P", SmileyClass.TONGUE_IMG);
                    	checkFor("B)", SmileyClass.GOGGLES_IMG);
                    	checkFor("B-)", SmileyClass.GOGGLES_IMG);
                    	checkFor("8)", SmileyClass.BIGEYES_IMG);
                    	checkFor("8-)", SmileyClass.BIGEYES_IMG);
                    }
                    
                    public void checkFor(String smileyText, ImageIcon smileyGraphic)
                    {
                    	if (e.getDocument() instanceof HTMLDocument) {
                            try {
                                HTMLDocument doc=(HTMLDocument)e.getDocument();
                                int start= Utilities.getRowStart(textPane,Math.max(0,e.getOffset()-1));
                                int end=Utilities.getWordStart(textPane,e.getOffset()+e.getLength());
                                String text=doc.getText(start, end-start);
 
                                int i=text.indexOf(smileyText);
                                while(i>=0) {
                                    final SimpleAttributeSet attrs=new SimpleAttributeSet(
                                       doc.getCharacterElement(start+i).getAttributes());
                                    if (StyleConstants.getIcon(attrs)==null) {
                                        StyleConstants.setIcon(attrs, smileyGraphic);
                                        doc.remove(start+i, smileyText.length());
                                        doc.insertString(start+i,":)", attrs);
                                    }
                                    i=text.indexOf(smileyText, i+smileyText.length());
                                }
                            } catch (BadLocationException e1) {
                                e1.printStackTrace();
                            }
                        }                        

                    }
                });
            }
            
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				
			}
        });
    }
    
}