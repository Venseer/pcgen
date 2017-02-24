/*
 *  Initiative - A role playing utility to track turns
 *  Copyright (C) 2002 Devon D Jones
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 * DamageDialog.java
 *
 */
package plugin.initiative.gui;

/**
 *
 */
public class DamageDialog extends javax.swing.JDialog
{
	/** OK_VALUE = 0 */
	public static final int OK_VALUE = 0;
	/** CANCEL_VALUE = 1 */
	public static final int CANCEL_VALUE = 1;

	private javax.swing.JButton bCancel;
	private javax.swing.JButton bOK;
	private javax.swing.JCheckBox subdualField;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JTextField damageField;
	private int damage;
	private int retVal;

	/**
	 * Creates new form DamageDialog 
	 * @param parent
	 * @param modal
	 */
	public DamageDialog(java.awt.Frame parent, boolean modal)
	{
		super(parent, modal);
		initComponents();
		setLocation(parent.getX() + 100, parent.getY() + 100);
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args)
	{
		new DamageDialog(new javax.swing.JFrame(), true).setVisible(true);
	}

	/**
	 * Get damage
	 * @return damage
	 */
	public int getDamage()
	{
		return damage;
	}

	/**
	 * Get return
	 * @return return
	 */
	public int getReturn()
	{
		return retVal;
	}

	/**
	 * Get subdual
	 * @return subdual
	 */
	public boolean getSubdual()
	{
		return subdualField.isSelected();
	}

	/**
	 * Check damage field
	 * @throws NumberFormatException
	 */
	public void checkDamageField() throws NumberFormatException
	{
		try
		{
			damage = Integer.parseInt(damageField.getText());
		}
		catch (NumberFormatException e)
		{
			damage = 0;
			damageField.setText(Integer.toString(damage));
			throw e;
		}
	}

	/**
	 * submit
	 */
	public void submit()
	{
		try
		{
			checkDamageField();
			retVal = 0;
			setVisible(false);
			dispose();
		}
		catch (NumberFormatException e)
		{
			// TODO Handle this?
		}
	}

	/**
	 * Cancel
	 */
	public void cancel()
	{
		retVal = 1;
		setVisible(false);
		dispose();
	}

	//Event handlers
	private void bCancelActionPerformed(java.awt.event.ActionEvent evt)
	{
		cancel();
	}

	private void bOKActionPerformed(java.awt.event.ActionEvent evt)
	{
		submit();
	}

	/** Closes the dialog 
	 * @param evt
	 */
	private void closeDialog(java.awt.event.WindowEvent evt)
	{
		cancel();
	}

	private void damageFieldActionPerformed(java.awt.event.ActionEvent evt)
	{
		try
		{
			checkDamageField();
		}
		catch (NumberFormatException e)
		{
			// TODO Handle this?
		}
	}

	private void damageFieldKeyTyped(java.awt.event.KeyEvent evt)
	{
		if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER)
		{
			submit();
		}
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	private void initComponents()
	{
		java.awt.GridBagConstraints gridBagConstraints;

		jLabel1 = new javax.swing.JLabel();
		damageField = new javax.swing.JTextField();
		subdualField = new javax.swing.JCheckBox();
		bOK = new javax.swing.JButton();
		bCancel = new javax.swing.JButton();

		getContentPane().setLayout(new java.awt.GridBagLayout());

		addWindowListener(new java.awt.event.WindowAdapter()
		{
            @Override
			public void windowClosing(java.awt.event.WindowEvent evt)
			{
				closeDialog(evt);
			}
		});

		jLabel1.setText("Damage");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		getContentPane().add(jLabel1, gridBagConstraints);

		damageField.addActionListener(this::damageFieldActionPerformed);
		damageField.addKeyListener(new java.awt.event.KeyAdapter()
		{
            @Override
			public void keyReleased(java.awt.event.KeyEvent evt)
			{
				damageFieldKeyTyped(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		getContentPane().add(damageField, gridBagConstraints);

		subdualField.setText("Subdual");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		getContentPane().add(subdualField, gridBagConstraints);

		bOK.setText("Ok");
		bOK.addActionListener(this::bOKActionPerformed);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 10);
		getContentPane().add(bOK, gridBagConstraints);

		bCancel.setText("Cancel");
		bCancel.addActionListener(this::bCancelActionPerformed);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
		getContentPane().add(bCancel, gridBagConstraints);

		pack();
	}
}
