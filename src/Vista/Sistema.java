/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vista;

import Modelo.cliente;
import Modelo.clienteDAO;
import Modelo.config;
import Modelo.detalle;
import Modelo.eventos;
import Modelo.login;
import Modelo.producto;
import Modelo.productoDAO;
import Modelo.proveedor;
import Modelo.proveedorDAO;
import Modelo.venta;
import Modelo.ventaDAO;
import Reportes.Excel;
import Reportes.grafico;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author surbi
 */
public final class Sistema extends javax.swing.JFrame {

    Date fechaVenta = new Date();
    String fechaActual = new SimpleDateFormat("dd-MM-yyyy").format(fechaVenta);
    cliente cl = new cliente();
    clienteDAO clDAO = new clienteDAO();
    proveedor pd = new proveedor();
    proveedorDAO pdDAO = new proveedorDAO();
    producto pto = new producto();
    productoDAO ptoDAO = new productoDAO();
    venta v = new venta();
    ventaDAO vDAO = new ventaDAO();
    detalle dv = new detalle();
    config config = new config();
    eventos event = new eventos();
    DefaultTableModel tmp = new DefaultTableModel();
    DefaultTableModel modelo = new DefaultTableModel();
    int item;
    double totalPagar = 0.00;

    public Sistema() {
        initComponents();
    }

    public Sistema(login priv) {
        initComponents();
        this.setLocationRelativeTo(null);
        txtIdCliente.setVisible(false);
        txtIdVentas.setVisible(false);
        txtIdProducto.setVisible(false);
        txtIdproducto.setVisible(false);
        txtIdProveedor.setVisible(false);
        txtIdProveedor.setVisible(false);
        txtIdProducto.setVisible(false);
        txtIdConfig.setVisible(false);
        txtIdConfig.setVisible(false);
        txtTelefonoCVenta.setVisible(false);
        txtDireccionCVenta.setVisible(false);
        txtRazonCVenta.setVisible(false);
        AutoCompleteDecorator.decorate(cbxProveedorProducto);
        ptoDAO.consultarProveedor(cbxProveedorProducto);
        listarConfig();
        if (priv.getRol().equals("Asistente")) {
            btnProducto.setEnabled(false);
            btnProveedor.setEnabled(false);
            btnConfig.setEnabled(false);
            btnUsuarios.setEnabled(false);
            labelVendedor.setText(priv.getNombre());
        } else {
            labelVendedor.setText(priv.getNombre());
        }
    }

    public void listarCliente() {
        List<cliente> listarCl = clDAO.listarCliente();
        modelo = (DefaultTableModel) tableCliente.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < listarCl.size(); i++) {
            ob[0] = listarCl.get(i).getId();
            ob[1] = listarCl.get(i).getDni();
            ob[2] = listarCl.get(i).getNombre();
            ob[3] = listarCl.get(i).getTelefono();
            ob[4] = listarCl.get(i).getDireccion();
            ob[5] = listarCl.get(i).getRazon();
            modelo.addRow(ob);
        }
        tableCliente.setModel(modelo);
    }

    public void listarProveedor() {
        List<proveedor> listarPd = pdDAO.listarProveedor();
        modelo = (DefaultTableModel) tableProveedor.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < listarPd.size(); i++) {
            ob[0] = listarPd.get(i).getId();
            ob[1] = listarPd.get(i).getRuc();
            ob[2] = listarPd.get(i).getNombre();
            ob[3] = listarPd.get(i).getTelefono();
            ob[4] = listarPd.get(i).getDireccion();
            ob[5] = listarPd.get(i).getRazon();
            modelo.addRow(ob);
        }
        tableProveedor.setModel(modelo);
    }

    public void listarProducto() {
        List<producto> listarPto = ptoDAO.listarProducto();
        modelo = (DefaultTableModel) tableProducto.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < listarPto.size(); i++) {
            ob[0] = listarPto.get(i).getId();
            ob[1] = listarPto.get(i).getCodigo();
            ob[2] = listarPto.get(i).getNombre();
            ob[3] = listarPto.get(i).getProveedor();
            ob[4] = listarPto.get(i).getStock();
            ob[5] = listarPto.get(i).getPrecio();
            modelo.addRow(ob);
        }
        tableProducto.setModel(modelo);
    }

    public void listarConfig() {
        config = ptoDAO.buscarDatos();
        txtIdConfig.setText("" + config.getId());
        txtRucConfig.setText("" + config.getRuc());
        txtNombreConfig.setText("" + config.getNombre());
        txtTelefonoConfig.setText("" + config.getTelefono());
        txtDireccionConfig.setText("" + config.getDireccion());
        txtRazonConfig.setText("" + config.getRazon());
    }

    public void listarVenta() {
        List<venta> listarVenta = vDAO.listarVentas();
        modelo = (DefaultTableModel) tableVentas.getModel();
        Object[] ob = new Object[4];
        for (int i = 0; i < listarVenta.size(); i++) {
            ob[0] = listarVenta.get(i).getId();
            ob[1] = listarVenta.get(i).getCliente();
            ob[2] = listarVenta.get(i).getVendedor();
            ob[3] = listarVenta.get(i).getTotal();
            modelo.addRow(ob);
        }
        tableVentas.setModel(modelo);
    }

    public void buscarVenta(String nombreCliente) {
        if (nombreCliente.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debes ingresar el nombre de un cliente!");
            return; // Si el campo está vacío, no hagas la búsqueda y muestra el mensaje.
        }

        List<venta> busquedaCliente = vDAO.buscarVentaCliente(nombreCliente);
        modelo = (DefaultTableModel) tableVentas.getModel();
        Object[] ob = new Object[4];
        modelo.setRowCount(0);

        for (int i = 0; i < busquedaCliente.size(); i++) {
            if (nombreCliente.isEmpty() || busquedaCliente.get(i).getCliente().equalsIgnoreCase(nombreCliente)) {
                ob[0] = busquedaCliente.get(i).getId();
                ob[1] = busquedaCliente.get(i).getCliente();
                ob[2] = busquedaCliente.get(i).getVendedor();
                ob[3] = busquedaCliente.get(i).getTotal();
                modelo.addRow(ob);
            }
        }
        tableVentas.setModel(modelo);
    }

    public void limpiarTable() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i--;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnNuevaVenta = new javax.swing.JButton();
        btnClientes = new javax.swing.JButton();
        btnProveedor = new javax.swing.JButton();
        btnProducto = new javax.swing.JButton();
        btnVentas = new javax.swing.JButton();
        btnConfig = new javax.swing.JButton();
        labelVendedor = new javax.swing.JLabel();
        btnUsuarios = new javax.swing.JButton();
        jLabel33 = new javax.swing.JLabel();
        jTabbedPane8 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnEliminarVenta = new javax.swing.JButton();
        txtCodigoVenta = new javax.swing.JTextField();
        txtDescripcionVenta = new javax.swing.JTextField();
        txtCantidadVenta = new javax.swing.JTextField();
        txtPrecioVenta = new javax.swing.JTextField();
        txtStockVenta = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableVenta = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtRucVenta = new javax.swing.JTextField();
        txtNombreVenta = new javax.swing.JTextField();
        btnImprimirVenta = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        labelTotalVenta = new javax.swing.JLabel();
        txtTelefonoCVenta = new javax.swing.JTextField();
        txtDireccionCVenta = new javax.swing.JTextField();
        txtRazonCVenta = new javax.swing.JTextField();
        txtIdproducto = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtDniCliente = new javax.swing.JTextField();
        txtNombreCliente = new javax.swing.JTextField();
        txtTelefonoCliente = new javax.swing.JTextField();
        txtDireccionCliente = new javax.swing.JTextField();
        txtRazonCliente = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableCliente = new javax.swing.JTable();
        btnGuardarCliente = new javax.swing.JButton();
        btnActualizarCliente = new javax.swing.JButton();
        btnEliminarCliente = new javax.swing.JButton();
        btnLimpiarCliente = new javax.swing.JButton();
        txtIdCliente = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        txtRucProveedor = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtNombreProveedor = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtTelefonoProveedor = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtDireccionProveedor = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtRazonProveedor = new javax.swing.JTextField();
        btnGuardarProvedor = new javax.swing.JButton();
        btnActualizarProveedor = new javax.swing.JButton();
        btnEliminarProveedor = new javax.swing.JButton();
        btnNuevoProveedor = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableProveedor = new javax.swing.JTable();
        txtIdProveedor = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        txtCodigoProducto = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txtNombreProducto = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txtStockProducto = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txtPrecioProducto = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        btnGuardarProducto = new javax.swing.JButton();
        btnActualizarProducto = new javax.swing.JButton();
        btnEliminarProducto = new javax.swing.JButton();
        btnNuevoProducto = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableProducto = new javax.swing.JTable();
        cbxProveedorProducto = new javax.swing.JComboBox<>();
        btnExcelProducto = new javax.swing.JButton();
        txtIdProducto = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableVentas = new javax.swing.JTable();
        btnBuscadorVentas = new javax.swing.JButton();
        txtbuscadorVentas = new javax.swing.JTextField();
        btnPdfVentas = new javax.swing.JButton();
        txtIdVentas = new javax.swing.JTextField();
        btnGraficar = new javax.swing.JButton();
        Midate = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        txtRucConfig = new javax.swing.JTextField();
        txtNombreConfig = new javax.swing.JTextField();
        txtTelefonoConfig = new javax.swing.JTextField();
        txtDireccionConfig = new javax.swing.JTextField();
        txtRazonConfig = new javax.swing.JTextField();
        btnActualizarConfig = new javax.swing.JButton();
        txtIdConfig = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(57, 72, 103));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/SKULLRIDERS Sistema.png"))); // NOI18N

        btnNuevaVenta.setBackground(new java.awt.Color(179, 190, 211));
        btnNuevaVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Nueva venta.png"))); // NOI18N
        btnNuevaVenta.setText(" Nueva Venta");
        btnNuevaVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaVentaActionPerformed(evt);
            }
        });

        btnClientes.setBackground(new java.awt.Color(179, 190, 211));
        btnClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/clientes.png"))); // NOI18N
        btnClientes.setText("Clientes");
        btnClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesActionPerformed(evt);
            }
        });

        btnProveedor.setBackground(new java.awt.Color(179, 190, 211));
        btnProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/proveedor.png"))); // NOI18N
        btnProveedor.setText(" Proveedores");
        btnProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProveedorActionPerformed(evt);
            }
        });

        btnProducto.setBackground(new java.awt.Color(179, 190, 211));
        btnProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/productos.png"))); // NOI18N
        btnProducto.setText(" Inventario");
        btnProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductoActionPerformed(evt);
            }
        });

        btnVentas.setBackground(new java.awt.Color(179, 190, 211));
        btnVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/reg ventas.png"))); // NOI18N
        btnVentas.setText(" Registro Ventas");
        btnVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVentasActionPerformed(evt);
            }
        });

        btnConfig.setBackground(new java.awt.Color(179, 190, 211));
        btnConfig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/config.png"))); // NOI18N
        btnConfig.setText(" Configuración");
        btnConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfigActionPerformed(evt);
            }
        });

        labelVendedor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelVendedor.setText("-");

        btnUsuarios.setBackground(new java.awt.Color(179, 190, 211));
        btnUsuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/equipo-de-usuario.png"))); // NOI18N
        btnUsuarios.setText("Usuarios");
        btnUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuariosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnNuevaVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(22, 22, 22)
                            .addComponent(jLabel1))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(labelVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(btnConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelVendedor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNuevaVenta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnUsuarios)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnClientes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnProveedor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnProducto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnVentas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnConfig)
                .addContainerGap(58, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 630));

        jLabel33.setBackground(new java.awt.Color(57, 72, 103));
        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/portada.png"))); // NOI18N
        getContentPane().add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 0, 810, 50));

        jTabbedPane8.setBackground(new java.awt.Color(155, 164, 181));

        jPanel2.setBackground(new java.awt.Color(155, 164, 181));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Código");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Descripción");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Cantidad");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Precio");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Stock Disponible");

        btnEliminarVenta.setBackground(new java.awt.Color(179, 190, 211));
        btnEliminarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/cierre.png"))); // NOI18N
        btnEliminarVenta.setBorder(null);
        btnEliminarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarVentaActionPerformed(evt);
            }
        });

        txtCodigoVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoVentaKeyTyped(evt);
            }
        });

        txtDescripcionVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionVentaKeyTyped(evt);
            }
        });

        txtCantidadVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadVentaActionPerformed(evt);
            }
        });
        txtCantidadVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantidadVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadVentaKeyTyped(evt);
            }
        });

        txtPrecioVenta.setEditable(false);

        tableVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CÓDIGO", "DESCRIPCIÓN", "CANTIDAD", "PRECIO", "TOTAL"
            }
        ));
        jScrollPane1.setViewportView(tableVenta);
        if (tableVenta.getColumnModel().getColumnCount() > 0) {
            tableVenta.getColumnModel().getColumn(0).setPreferredWidth(30);
            tableVenta.getColumnModel().getColumn(1).setPreferredWidth(100);
            tableVenta.getColumnModel().getColumn(2).setPreferredWidth(30);
            tableVenta.getColumnModel().getColumn(3).setPreferredWidth(30);
            tableVenta.getColumnModel().getColumn(4).setPreferredWidth(40);
        }

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("DNI / RUC");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("NOMBRE");

        txtRucVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRucVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRucVentaKeyTyped(evt);
            }
        });

        txtNombreVenta.setEditable(false);

        btnImprimirVenta.setBackground(new java.awt.Color(179, 190, 211));
        btnImprimirVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/imprimir.png"))); // NOI18N
        btnImprimirVenta.setBorder(null);
        btnImprimirVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirVentaActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/monedas.png"))); // NOI18N
        jLabel9.setText("Total a Pagar");

        labelTotalVenta.setText("-----");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(txtCodigoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtDescripcionVenta))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(txtCantidadVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(txtPrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(43, 43, 43))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtStockVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtIdproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnEliminarVenta))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 757, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtRucVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(txtNombreVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtTelefonoCVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtDireccionCVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtRazonCVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnImprimirVenta)
                                .addGap(48, 48, 48)
                                .addComponent(jLabel9)
                                .addGap(41, 41, 41)
                                .addComponent(labelTotalVenta)
                                .addGap(9, 9, 9)))))
                .addGap(51, 51, 51))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCodigoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDescripcionVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCantidadVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtStockVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIdproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnEliminarVenta))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(labelTotalVenta)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnImprimirVenta)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtRucVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNombreVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTelefonoCVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDireccionCVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtRazonCVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jTabbedPane8.addTab("tab1", jPanel2);

        jPanel3.setBackground(new java.awt.Color(155, 164, 181));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("DNI / RUC:");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setText("Nombre:");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setText("Telefono:");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setText("Dirección:");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setText("Razón Social:");

        txtDniCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDniClienteKeyTyped(evt);
            }
        });

        txtNombreCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreClienteKeyTyped(evt);
            }
        });

        txtTelefonoCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoClienteKeyTyped(evt);
            }
        });

        txtDireccionCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDireccionClienteKeyTyped(evt);
            }
        });

        txtRazonCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRazonClienteKeyTyped(evt);
            }
        });

        tableCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "DNI / RUC", "NOMBRE", "TELEFONO", "DIRECCIÓN", "RAZÓN SOCIAL"
            }
        ));
        tableCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableClienteMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableCliente);
        if (tableCliente.getColumnModel().getColumnCount() > 0) {
            tableCliente.getColumnModel().getColumn(0).setPreferredWidth(30);
            tableCliente.getColumnModel().getColumn(1).setPreferredWidth(50);
            tableCliente.getColumnModel().getColumn(2).setPreferredWidth(100);
            tableCliente.getColumnModel().getColumn(3).setPreferredWidth(50);
            tableCliente.getColumnModel().getColumn(4).setPreferredWidth(50);
            tableCliente.getColumnModel().getColumn(5).setPreferredWidth(80);
        }

        btnGuardarCliente.setBackground(new java.awt.Color(179, 190, 211));
        btnGuardarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/guardar.png"))); // NOI18N
        btnGuardarCliente.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnGuardarCliente.setBorderPainted(false);
        btnGuardarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarClienteActionPerformed(evt);
            }
        });

        btnActualizarCliente.setBackground(new java.awt.Color(179, 190, 211));
        btnActualizarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/actualizar.png"))); // NOI18N
        btnActualizarCliente.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnActualizarCliente.setBorderPainted(false);
        btnActualizarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnActualizarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarClienteActionPerformed(evt);
            }
        });

        btnEliminarCliente.setBackground(new java.awt.Color(179, 190, 211));
        btnEliminarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/basura.png"))); // NOI18N
        btnEliminarCliente.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnEliminarCliente.setBorderPainted(false);
        btnEliminarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarClienteActionPerformed(evt);
            }
        });

        btnLimpiarCliente.setBackground(new java.awt.Color(179, 190, 211));
        btnLimpiarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/limpio.png"))); // NOI18N
        btnLimpiarCliente.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnLimpiarCliente.setBorderPainted(false);
        btnLimpiarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLimpiarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel11)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtTelefonoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtDniCliente)
                                .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtDireccionCliente)
                                .addComponent(txtRazonCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnGuardarCliente)
                        .addGap(27, 27, 27)
                        .addComponent(btnActualizarCliente)
                        .addGap(27, 27, 27)
                        .addComponent(btnEliminarCliente)
                        .addGap(29, 29, 29)
                        .addComponent(btnLimpiarCliente)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtDniCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtTelefonoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtDireccionCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtRazonCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(57, 57, 57)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGuardarCliente)
                    .addComponent(btnActualizarCliente)
                    .addComponent(btnEliminarCliente)
                    .addComponent(btnLimpiarCliente))
                .addContainerGap(163, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );

        jTabbedPane8.addTab("tab2", jPanel3);

        jPanel4.setBackground(new java.awt.Color(155, 164, 181));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setText("DNI / RUC:");

        txtRucProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRucProveedorKeyTyped(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel17.setText("Nombre:");

        txtNombreProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreProveedorKeyTyped(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel18.setText("Telefono:");

        txtTelefonoProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoProveedorKeyTyped(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel19.setText("Dirección:");

        txtDireccionProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDireccionProveedorKeyTyped(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel20.setText("Razón Social:");

        txtRazonProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRazonProveedorKeyTyped(evt);
            }
        });

        btnGuardarProvedor.setBackground(new java.awt.Color(179, 190, 211));
        btnGuardarProvedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/guardar.png"))); // NOI18N
        btnGuardarProvedor.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnGuardarProvedor.setBorderPainted(false);
        btnGuardarProvedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarProvedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarProvedorActionPerformed(evt);
            }
        });

        btnActualizarProveedor.setBackground(new java.awt.Color(179, 190, 211));
        btnActualizarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/actualizar.png"))); // NOI18N
        btnActualizarProveedor.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnActualizarProveedor.setBorderPainted(false);
        btnActualizarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnActualizarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarProveedorActionPerformed(evt);
            }
        });

        btnEliminarProveedor.setBackground(new java.awt.Color(179, 190, 211));
        btnEliminarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/basura.png"))); // NOI18N
        btnEliminarProveedor.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnEliminarProveedor.setBorderPainted(false);
        btnEliminarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProveedorActionPerformed(evt);
            }
        });

        btnNuevoProveedor.setBackground(new java.awt.Color(179, 190, 211));
        btnNuevoProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/limpio.png"))); // NOI18N
        btnNuevoProveedor.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnNuevoProveedor.setBorderPainted(false);
        btnNuevoProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNuevoProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoProveedorActionPerformed(evt);
            }
        });

        tableProveedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "DNI / RUC", "NOMBRE", "TELEFONO", "DIRECCIÓN", "RAZÓN SOCIAL"
            }
        ));
        tableProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableProveedorMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tableProveedor);
        if (tableProveedor.getColumnModel().getColumnCount() > 0) {
            tableProveedor.getColumnModel().getColumn(0).setPreferredWidth(20);
            tableProveedor.getColumnModel().getColumn(1).setPreferredWidth(30);
            tableProveedor.getColumnModel().getColumn(2).setPreferredWidth(100);
            tableProveedor.getColumnModel().getColumn(3).setPreferredWidth(50);
            tableProveedor.getColumnModel().getColumn(4).setPreferredWidth(50);
            tableProveedor.getColumnModel().getColumn(5).setPreferredWidth(80);
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel16)
                            .addComponent(jLabel18)
                            .addComponent(jLabel19)
                            .addComponent(jLabel20))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtTelefonoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtRucProveedor)
                                .addComponent(txtNombreProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtDireccionProveedor)
                                .addComponent(txtRazonProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIdProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnGuardarProvedor)
                        .addGap(27, 27, 27)
                        .addComponent(btnActualizarProveedor)
                        .addGap(27, 27, 27)
                        .addComponent(btnEliminarProveedor)
                        .addGap(29, 29, 29)
                        .addComponent(btnNuevoProveedor)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtRucProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIdProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtNombreProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtTelefonoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtDireccionProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtRazonProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGuardarProvedor)
                    .addComponent(btnActualizarProveedor)
                    .addComponent(btnEliminarProveedor)
                    .addComponent(btnNuevoProveedor))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 567, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane8.addTab("tab3", jPanel4);

        jPanel5.setBackground(new java.awt.Color(155, 164, 181));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel21.setText("Código");

        txtCodigoProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoProductoKeyTyped(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel22.setText("Descripción");

        txtNombreProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreProductoKeyTyped(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel23.setText("Cantidad");

        txtStockProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtStockProductoKeyTyped(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel24.setText("Precio");

        txtPrecioProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioProductoKeyTyped(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel25.setText("Proveedor");

        btnGuardarProducto.setBackground(new java.awt.Color(179, 190, 211));
        btnGuardarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/guardar.png"))); // NOI18N
        btnGuardarProducto.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnGuardarProducto.setBorderPainted(false);
        btnGuardarProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarProductoActionPerformed(evt);
            }
        });

        btnActualizarProducto.setBackground(new java.awt.Color(179, 190, 211));
        btnActualizarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/actualizar.png"))); // NOI18N
        btnActualizarProducto.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnActualizarProducto.setBorderPainted(false);
        btnActualizarProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnActualizarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarProductoActionPerformed(evt);
            }
        });

        btnEliminarProducto.setBackground(new java.awt.Color(179, 190, 211));
        btnEliminarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/basura.png"))); // NOI18N
        btnEliminarProducto.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnEliminarProducto.setBorderPainted(false);
        btnEliminarProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProductoActionPerformed(evt);
            }
        });

        btnNuevoProducto.setBackground(new java.awt.Color(179, 190, 211));
        btnNuevoProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/limpio.png"))); // NOI18N
        btnNuevoProducto.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnNuevoProducto.setBorderPainted(false);
        btnNuevoProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNuevoProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoProductoActionPerformed(evt);
            }
        });

        tableProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CÓDIGO", "DESCRIPCIÓN", "PROVEEDOR", "STOCK", "PRECIO"
            }
        ));
        tableProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableProductoMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tableProducto);
        if (tableProducto.getColumnModel().getColumnCount() > 0) {
            tableProducto.getColumnModel().getColumn(0).setPreferredWidth(30);
            tableProducto.getColumnModel().getColumn(1).setPreferredWidth(50);
            tableProducto.getColumnModel().getColumn(2).setPreferredWidth(100);
            tableProducto.getColumnModel().getColumn(3).setPreferredWidth(80);
            tableProducto.getColumnModel().getColumn(4).setPreferredWidth(50);
            tableProducto.getColumnModel().getColumn(5).setPreferredWidth(50);
        }

        cbxProveedorProducto.setEditable(true);
        cbxProveedorProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxProveedorProductoActionPerformed(evt);
            }
        });

        btnExcelProducto.setBackground(new java.awt.Color(179, 190, 211));
        btnExcelProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/archivo-excel.png"))); // NOI18N
        btnExcelProducto.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnExcelProducto.setBorderPainted(false);
        btnExcelProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExcelProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcelProductoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btnGuardarProducto)
                        .addGap(18, 18, 18)
                        .addComponent(btnActualizarProducto)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminarProducto)
                        .addGap(18, 18, 18)
                        .addComponent(btnNuevoProducto)
                        .addGap(18, 18, 18)
                        .addComponent(btnExcelProducto))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(jLabel21)
                            .addComponent(jLabel23)
                            .addComponent(jLabel24)
                            .addComponent(jLabel25))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtStockProducto, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(txtCodigoProducto, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombreProducto, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(txtPrecioProducto, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxProveedorProducto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtIdProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtCodigoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIdProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(txtNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(txtStockProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(txtPrecioProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(cbxProveedorProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGuardarProducto)
                    .addComponent(btnActualizarProducto)
                    .addComponent(btnEliminarProducto)
                    .addComponent(btnNuevoProducto)
                    .addComponent(btnExcelProducto))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 567, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane8.addTab("tab4", jPanel5);

        jPanel6.setBackground(new java.awt.Color(155, 164, 181));

        tableVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CLIENTE", "VENDEDOR", "TOTAL"
            }
        ));
        tableVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableVentasMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tableVentas);
        if (tableVentas.getColumnModel().getColumnCount() > 0) {
            tableVentas.getColumnModel().getColumn(0).setPreferredWidth(50);
            tableVentas.getColumnModel().getColumn(1).setPreferredWidth(100);
            tableVentas.getColumnModel().getColumn(2).setPreferredWidth(100);
            tableVentas.getColumnModel().getColumn(3).setPreferredWidth(50);
        }

        btnBuscadorVentas.setBackground(new java.awt.Color(179, 190, 211));
        btnBuscadorVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/busqueda.png"))); // NOI18N
        btnBuscadorVentas.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnBuscadorVentas.setBorderPainted(false);
        btnBuscadorVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscadorVentasActionPerformed(evt);
            }
        });

        btnPdfVentas.setBackground(new java.awt.Color(179, 190, 211));
        btnPdfVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/archivo-pdf.png"))); // NOI18N
        btnPdfVentas.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnPdfVentas.setBorderPainted(false);
        btnPdfVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPdfVentasActionPerformed(evt);
            }
        });

        btnGraficar.setBackground(new java.awt.Color(179, 190, 211));
        btnGraficar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/grafico-pastel-alt.png"))); // NOI18N
        btnGraficar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnGraficar.setBorderPainted(false);
        btnGraficar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGraficarActionPerformed(evt);
            }
        });

        jLabel10.setText("Seleccionar:");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 798, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscadorVentas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtbuscadorVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIdVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(btnPdfVentas)
                        .addGap(18, 18, 18)
                        .addComponent(btnGraficar)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Midate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnBuscadorVentas)
                        .addComponent(btnPdfVentas)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtbuscadorVentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIdVentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(btnGraficar))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Midate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );

        jTabbedPane8.addTab("tab5", jPanel6);

        jPanel7.setBackground(new java.awt.Color(155, 164, 181));

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("DATOS DE LA EMPRESA");

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel27.setText("Ruc:");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel28.setText("Nombre:");

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel29.setText("Teléfono:");

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel30.setText("Dirección:");

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel31.setText("Razón Social:");

        btnActualizarConfig.setBackground(new java.awt.Color(179, 190, 211));
        btnActualizarConfig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/actualizar.png"))); // NOI18N
        btnActualizarConfig.setText(" Actualizar ");
        btnActualizarConfig.setToolTipText("");
        btnActualizarConfig.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(155, 164, 181), new java.awt.Color(155, 164, 181), null, null));
        btnActualizarConfig.setBorderPainted(false);
        btnActualizarConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarConfigActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(200, 200, 200)
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27)
                            .addComponent(jLabel30)
                            .addComponent(txtRucConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDireccionConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIdConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel31)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel28)
                                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtRazonConfig, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtNombreConfig, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(30, 30, 30)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel29)
                                    .addComponent(txtTelefonoConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnActualizarConfig))))))
                .addContainerGap(180, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel26)
                .addGap(23, 23, 23)
                .addComponent(txtIdConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jLabel28)
                    .addComponent(jLabel29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRucConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombreConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelefonoConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(103, 103, 103)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(jLabel31))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDireccionConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRazonConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnActualizarConfig))
                .addContainerGap(229, Short.MAX_VALUE))
        );

        jTabbedPane8.addTab("tab6", jPanel7);

        getContentPane().add(jTabbedPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 20, 810, 610));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevaVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaVentaActionPerformed
        jTabbedPane8.setSelectedIndex(0);
    }//GEN-LAST:event_btnNuevaVentaActionPerformed

    private void txtCantidadVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadVentaActionPerformed

    private void cbxProveedorProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxProveedorProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxProveedorProductoActionPerformed

    private void btnGuardarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarClienteActionPerformed
        if (!"".equals(txtDniCliente.getText()) || !"".equals(txtNombreCliente.getText()) || !"".equals(txtTelefonoCliente.getText()) || !"".equals(txtDireccionCliente.getText())) {
            cl.setDni(Integer.parseInt(txtDniCliente.getText()));
            cl.setNombre(txtNombreCliente.getText());
            cl.setTelefono(Integer.parseInt(txtTelefonoCliente.getText()));
            cl.setDireccion(txtDireccionCliente.getText());
            cl.setRazon(txtRazonCliente.getText());
            clDAO.registrarCliente(cl);
            limpiarTable();
            limpiarCliente();
            listarCliente();
            JOptionPane.showMessageDialog(null, "Cliente registrado!");
        } else {
            JOptionPane.showMessageDialog(null, "Los campos estan vacios");
        }
    }//GEN-LAST:event_btnGuardarClienteActionPerformed

    private void btnClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesActionPerformed
        limpiarTable();
        listarCliente();
        jTabbedPane8.setSelectedIndex(1);
    }//GEN-LAST:event_btnClientesActionPerformed

    private void tableClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableClienteMouseClicked
        int fila = tableCliente.rowAtPoint(evt.getPoint());
        txtIdCliente.setText(tableCliente.getValueAt(fila, 0).toString());
        txtDniCliente.setText(tableCliente.getValueAt(fila, 1).toString());
        txtNombreCliente.setText(tableCliente.getValueAt(fila, 2).toString());
        txtTelefonoCliente.setText(tableCliente.getValueAt(fila, 3).toString());
        txtDireccionCliente.setText(tableCliente.getValueAt(fila, 4).toString());
        txtRazonCliente.setText(tableCliente.getValueAt(fila, 5).toString());
    }//GEN-LAST:event_tableClienteMouseClicked

    private void btnEliminarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarClienteActionPerformed
        if (!"".equals(txtIdCliente.getText())) {
            int question = JOptionPane.showConfirmDialog(null, "Estas seguro de eliminar");
            if (question == 0) {
                int id = Integer.parseInt(txtIdCliente.getText());
                clDAO.eliminarCliente(id);
                limpiarTable();
                limpiarCliente();
                listarCliente();
                JOptionPane.showMessageDialog(null, "Cliente eliminado!");
            }
        }
    }//GEN-LAST:event_btnEliminarClienteActionPerformed

    private void btnLimpiarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarClienteActionPerformed
        limpiarCliente();
    }//GEN-LAST:event_btnLimpiarClienteActionPerformed

    private void btnActualizarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarClienteActionPerformed
        if ("".equals(txtIdCliente.getText())) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        } else {
            if (!"".equals(txtDniCliente.getText()) && !"".equals(txtNombreCliente.getText()) && !"".equals(txtTelefonoCliente.getText()) && !"".equals(txtDireccionCliente.getText())) {
                cl.setDni(Integer.parseInt(txtDniCliente.getText()));
                cl.setNombre(txtNombreCliente.getText());
                cl.setTelefono(Integer.parseInt(txtTelefonoCliente.getText()));
                cl.setDireccion(txtDireccionCliente.getText());
                cl.setRazon(txtRazonCliente.getText());
                cl.setId(Integer.parseInt(txtIdCliente.getText()));

                clDAO.modificarCliente(cl);
                limpiarTable();
                limpiarCliente();
                listarCliente();
                JOptionPane.showMessageDialog(null, "Cliente actualizado!");
            } else {
                JOptionPane.showMessageDialog(null, "Los campos están vacios");
            }
        }
    }//GEN-LAST:event_btnActualizarClienteActionPerformed

    private void btnGuardarProvedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProvedorActionPerformed
        if (!"".equals(txtRucProveedor.getText()) || !"".equals(txtNombreProveedor.getText()) || !"".equals(txtTelefonoProveedor.getText()) || !"".equals(txtDireccionProveedor.getText())) {
            pd.setRuc(Integer.parseInt(txtRucProveedor.getText()));
            pd.setNombre(txtNombreProveedor.getText());
            pd.setTelefono(Integer.parseInt(txtTelefonoProveedor.getText()));
            pd.setDireccion(txtDireccionProveedor.getText());
            pd.setRazon(txtRazonProveedor.getText());
            pdDAO.registrarProveedor(pd);
            limpiarTable();
            limpiarProveedor();
            listarProveedor();
            JOptionPane.showMessageDialog(null, "Proveedor registrado!");
        } else {
            JOptionPane.showMessageDialog(null, "Los campos estan vacios");
        }
    }//GEN-LAST:event_btnGuardarProvedorActionPerformed

    private void btnProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProveedorActionPerformed
        limpiarTable();
        listarProveedor();
        jTabbedPane8.setSelectedIndex(2);
    }//GEN-LAST:event_btnProveedorActionPerformed

    private void tableProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableProveedorMouseClicked
        int fila = tableProveedor.rowAtPoint(evt.getPoint());
        txtIdProveedor.setText(tableProveedor.getValueAt(fila, 0).toString());
        txtRucProveedor.setText(tableProveedor.getValueAt(fila, 1).toString());
        txtNombreProveedor.setText(tableProveedor.getValueAt(fila, 2).toString());
        txtTelefonoProveedor.setText(tableProveedor.getValueAt(fila, 3).toString());
        txtDireccionProveedor.setText(tableProveedor.getValueAt(fila, 4).toString());
        txtRazonProveedor.setText(tableProveedor.getValueAt(fila, 5).toString());
    }//GEN-LAST:event_tableProveedorMouseClicked

    private void btnEliminarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProveedorActionPerformed
        if (!"".equals(txtIdProveedor.getText())) {
            int question = JOptionPane.showConfirmDialog(null, "Estas seguro de eliminar");
            if (question == 0) {
                int id = Integer.parseInt(txtIdProveedor.getText());
                pdDAO.eliminarProveedor(id);
                limpiarTable();
                limpiarProveedor();
                listarProveedor();
                JOptionPane.showMessageDialog(null, "Proveedor eliminado!");
            }
        }
    }//GEN-LAST:event_btnEliminarProveedorActionPerformed

    private void btnNuevoProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProveedorActionPerformed
        limpiarProveedor();
    }//GEN-LAST:event_btnNuevoProveedorActionPerformed

    private void btnActualizarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarProveedorActionPerformed
        if ("".equals(txtIdProveedor.getText())) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        } else {
            if (!"".equals(txtRucProveedor.getText()) && !"".equals(txtNombreProveedor.getText()) && !"".equals(txtTelefonoProveedor.getText()) && !"".equals(txtDireccionProveedor.getText())) {
                pd.setRuc(Integer.parseInt(txtRucProveedor.getText()));
                pd.setNombre(txtNombreProveedor.getText());
                pd.setTelefono(Integer.parseInt(txtTelefonoProveedor.getText()));
                pd.setDireccion(txtDireccionProveedor.getText());
                pd.setRazon(txtRazonProveedor.getText());
                pd.setId(Integer.parseInt(txtIdProveedor.getText()));

                pdDAO.modificarProveedor(pd);
                limpiarTable();
                limpiarProveedor();
                listarProveedor();
                JOptionPane.showMessageDialog(null, "Proveedor actualizado!");
            } else {
                JOptionPane.showMessageDialog(null, "Los campos están vacios");
            }
        }
    }//GEN-LAST:event_btnActualizarProveedorActionPerformed

    private void btnGuardarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProductoActionPerformed
        if (!"".equals(txtCodigoProducto.getText()) || !"".equals(txtNombreProducto.getText()) || !"".equals(cbxProveedorProducto.getSelectedItem()) || !"".equals(txtStockProducto.getText()) || !"".equals(txtPrecioProducto.getText())) {
            pto.setCodigo(txtCodigoProducto.getText());
            pto.setNombre(txtNombreProducto.getText());
            pto.setProveedor(cbxProveedorProducto.getSelectedItem().toString());
            pto.setStock(Integer.parseInt(txtStockProducto.getText()));
            pto.setPrecio(Double.parseDouble(txtPrecioProducto.getText()));
            ptoDAO.registrarProducto(pto);
            limpiarTable();
            limpiarProducto();
            listarProducto();
            JOptionPane.showMessageDialog(null, "Producto registrado!");
        } else {
            JOptionPane.showMessageDialog(null, "Los campos estan vacios");
        }
    }//GEN-LAST:event_btnGuardarProductoActionPerformed

    private void btnProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductoActionPerformed
        limpiarTable();
        listarProducto();
        jTabbedPane8.setSelectedIndex(3);
    }//GEN-LAST:event_btnProductoActionPerformed

    private void tableProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableProductoMouseClicked
        int fila = tableProducto.rowAtPoint(evt.getPoint());
        txtIdProducto.setText(tableProducto.getValueAt(fila, 0).toString());
        txtCodigoProducto.setText(tableProducto.getValueAt(fila, 1).toString());
        txtNombreProducto.setText(tableProducto.getValueAt(fila, 2).toString());
        cbxProveedorProducto.setSelectedItem(tableProducto.getValueAt(fila, 3).toString());
        txtStockProducto.setText(tableProducto.getValueAt(fila, 4).toString());
        txtPrecioProducto.setText(tableProducto.getValueAt(fila, 5).toString());
    }//GEN-LAST:event_tableProductoMouseClicked

    private void btnEliminarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProductoActionPerformed
        if (!"".equals(txtIdProducto.getText())) {
            int question = JOptionPane.showConfirmDialog(null, "Estas seguro de eliminar");
            if (question == 0) {
                int id = Integer.parseInt(txtIdProducto.getText());
                ptoDAO.eliminarProducto(id);
                limpiarTable();
                limpiarProducto();
                listarProducto();
                JOptionPane.showMessageDialog(null, "Producto eliminado!");
            }
        }
    }//GEN-LAST:event_btnEliminarProductoActionPerformed

    private void btnActualizarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarProductoActionPerformed
        if ("".equals(txtIdProducto.getText())) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        } else {
            if (!"".equals(txtCodigoProducto.getText()) && !"".equals(txtNombreProducto.getText()) && !"".equals(cbxProveedorProducto.getSelectedItem()) && !"".equals(txtStockProducto.getText()) && !"".equals(txtPrecioProducto.getText())) {
                pto.setCodigo(txtCodigoProducto.getText());
                pto.setNombre(txtNombreProducto.getText());
                pto.setProveedor(cbxProveedorProducto.getSelectedItem().toString());
                pto.setStock(Integer.parseInt(txtStockProducto.getText()));
                pto.setPrecio(Double.parseDouble(txtPrecioProducto.getText()));
                pto.setId(Integer.parseInt(txtIdProducto.getText()));

                ptoDAO.modificarProducto(pto);
                limpiarTable();
                limpiarProducto();
                listarProducto();
                JOptionPane.showMessageDialog(null, "Producto actualizado!");
            } else {
                JOptionPane.showMessageDialog(null, "Los campos están vacios");
            }
        }
    }//GEN-LAST:event_btnActualizarProductoActionPerformed

    private void btnNuevoProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProductoActionPerformed
        limpiarProducto();
    }//GEN-LAST:event_btnNuevoProductoActionPerformed

    private void btnExcelProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelProductoActionPerformed
        Excel.reporte();
    }//GEN-LAST:event_btnExcelProductoActionPerformed

    private void txtCodigoVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoVentaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtCodigoVenta.getText())) {
                String cod = txtCodigoVenta.getText();
                pto = ptoDAO.buscarProducto(cod);
                if (pto.getNombre() != null) {
                    txtDescripcionVenta.setText("" + pto.getNombre());
                    txtPrecioVenta.setText("" + pto.getPrecio());
                    txtStockVenta.setText("" + pto.getStock());
                    txtCantidadVenta.requestFocus();
                } else {
                    limpiarVenta();
                    txtCodigoVenta.requestFocus();
                    JOptionPane.showMessageDialog(null, "Ingrese el codigo del producto");
                }
            } else {
                txtCodigoVenta.requestFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoVentaKeyPressed

    private void txtCantidadVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadVentaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtCantidadVenta.getText())) {
                String cod = txtCodigoVenta.getText();
                String desc = txtDescripcionVenta.getText();
                int cant = Integer.parseInt(txtCantidadVenta.getText());
                double prec = Double.parseDouble(txtPrecioVenta.getText());
                double total = cant * prec;
                int stock = Integer.parseInt(txtStockVenta.getText());
                if (stock >= cant) {
                    item = item++;
                    tmp = (DefaultTableModel) tableVenta.getModel();
                    for (int i = 0; i < tableVenta.getRowCount(); i++) {
                        if (tableVenta.getValueAt(i, 1).equals(txtDescripcionVenta.getText())) {
                            JOptionPane.showMessageDialog(null, "El producto ya está registrado!");
                            return;
                        }
                    }
                    ArrayList lista = new ArrayList();
                    lista.add(item);
                    lista.add(cod);
                    lista.add(desc);
                    lista.add(cant);
                    lista.add(prec);
                    lista.add(total);
                    Object[] O = new Object[5];
                    O[0] = lista.get(1);
                    O[1] = lista.get(2);
                    O[2] = lista.get(3);
                    O[3] = lista.get(4);
                    O[4] = lista.get(5);
                    tmp.addRow(O);
                    tableVenta.setModel(tmp);
                    totalPagar();
                    limpiarVenta();
                    txtCodigoVenta.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(null, "Stock no disponible!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese cantidad");
            }
        }
    }//GEN-LAST:event_txtCantidadVentaKeyPressed

    private void btnEliminarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarVentaActionPerformed
        modelo = (DefaultTableModel) tableVenta.getModel();
        modelo.removeRow(tableVenta.getSelectedRow());
        totalPagar();
        txtCodigoVenta.requestFocus();
    }//GEN-LAST:event_btnEliminarVentaActionPerformed

    private void txtRucVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRucVentaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtRucVenta.getText())) {
                int dni = Integer.parseInt(txtRucVenta.getText());
                cl = clDAO.buscarCliente(dni);
                if (cl.getNombre() != null) {
                    txtNombreVenta.setText("" + cl.getNombre());
                    txtTelefonoCVenta.setText("" + cl.getTelefono());
                    txtDireccionCVenta.setText("" + cl.getDireccion());
                    txtRazonCVenta.setText("" + cl.getRazon());
                } else {
                    txtRucVenta.setText("");
                    JOptionPane.showMessageDialog(null, "El cliente no existe!");
                }
            }
        }
    }//GEN-LAST:event_txtRucVentaKeyPressed

    private void btnImprimirVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirVentaActionPerformed
        if (tableVenta.getRowCount() > 0) {
            if (!"".equals(txtNombreVenta.getText())) {
                registrarVenta();
                registrarDetalle();
                actualizarStock();
                try {
                    pdf();
                } catch (DocumentException | IOException ex) {
                    System.out.println(ex.toString());
                } catch (URISyntaxException ex) {
                    Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
                }
                limpiartableVenta();
                limpiarclienteVenta();
            } else {
                JOptionPane.showMessageDialog(null, "Debes buscar un cliente!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No hay productos en la venta!");
        }

    }//GEN-LAST:event_btnImprimirVentaActionPerformed

    private void txtCodigoVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoVentaKeyTyped
        event.textAndNumberKeyPress(evt);
    }//GEN-LAST:event_txtCodigoVentaKeyTyped

    private void txtDescripcionVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionVentaKeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtDescripcionVentaKeyTyped

    private void txtCantidadVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadVentaKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtCantidadVentaKeyTyped

    private void txtRucVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRucVentaKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtRucVentaKeyTyped

    private void txtDniClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDniClienteKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtDniClienteKeyTyped

    private void txtNombreClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreClienteKeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtNombreClienteKeyTyped

    private void txtTelefonoClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoClienteKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtTelefonoClienteKeyTyped

    private void txtDireccionClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionClienteKeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtDireccionClienteKeyTyped

    private void txtRazonClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRazonClienteKeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtRazonClienteKeyTyped

    private void txtRucProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRucProveedorKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtRucProveedorKeyTyped

    private void txtNombreProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreProveedorKeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtNombreProveedorKeyTyped

    private void txtTelefonoProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoProveedorKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtTelefonoProveedorKeyTyped

    private void txtDireccionProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionProveedorKeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtDireccionProveedorKeyTyped

    private void txtRazonProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRazonProveedorKeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtRazonProveedorKeyTyped

    private void txtCodigoProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoProductoKeyTyped
        event.textAndNumberKeyPress(evt);
    }//GEN-LAST:event_txtCodigoProductoKeyTyped

    private void txtNombreProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreProductoKeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtNombreProductoKeyTyped

    private void txtStockProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtStockProductoKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtStockProductoKeyTyped

    private void txtPrecioProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioProductoKeyTyped
        event.numberDecimalKeyPress(evt, txtPrecioProducto);
    }//GEN-LAST:event_txtPrecioProductoKeyTyped

    private void btnActualizarConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarConfigActionPerformed
        if (!"".equals(txtRucConfig.getText()) || !"".equals(txtNombreConfig.getText()) || !"".equals(txtTelefonoConfig.getText()) || !"".equals(txtDireccionConfig.getText())) {
            config.setRuc(Integer.parseInt(txtRucConfig.getText()));
            config.setNombre(txtNombreConfig.getText());
            config.setTelefono(Integer.parseInt(txtTelefonoConfig.getText()));
            config.setDireccion(txtDireccionConfig.getText());
            config.setRazon(txtRazonConfig.getText());
            config.setId(Integer.parseInt(txtIdConfig.getText()));

            ptoDAO.modificarDatos(config);
            listarConfig();
            JOptionPane.showMessageDialog(null, "Datos de la empresa modificados!");
        } else {
            JOptionPane.showMessageDialog(null, "Los campos están vacios");
        }
    }//GEN-LAST:event_btnActualizarConfigActionPerformed

    private void btnConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfigActionPerformed
        jTabbedPane8.setSelectedIndex(5);
    }//GEN-LAST:event_btnConfigActionPerformed

    private void btnVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVentasActionPerformed
        limpiarTable();
        listarVenta();
        jTabbedPane8.setSelectedIndex(4);
    }//GEN-LAST:event_btnVentasActionPerformed

    private void tableVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableVentasMouseClicked
        int fila = tableVentas.rowAtPoint(evt.getPoint());
        txtIdVentas.setText(tableVentas.getValueAt(fila, 0).toString());
    }//GEN-LAST:event_tableVentasMouseClicked

    private void btnPdfVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPdfVentasActionPerformed
        try {
            int id = Integer.parseInt(txtIdVentas.getText());
            String pdfPath = "/pdf/venta" + id + ".pdf";
            URL pdfUrl = getClass().getResource(pdfPath);

            if (pdfUrl != null) {
                File file = new File(pdfUrl.toURI());
                Desktop.getDesktop().open(file);
            } else {
                System.out.println("No se pudo encontrar el archivo PDF.");
            }
        } catch (NumberFormatException | URISyntaxException | IOException ex) {
            Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnPdfVentasActionPerformed

    private void btnGraficarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGraficarActionPerformed
        String fechaReporte = new SimpleDateFormat("dd-MM-yyyy").format(Midate.getDate());
        grafico.graficar(fechaReporte);
    }//GEN-LAST:event_btnGraficarActionPerformed

    private void btnUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuariosActionPerformed
        registro reg = new registro();
        reg.setVisible(true);
    }//GEN-LAST:event_btnUsuariosActionPerformed

    private void btnBuscadorVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscadorVentasActionPerformed
        String buscador = txtbuscadorVentas.getText();
        buscarVenta(buscador);
    }//GEN-LAST:event_btnBuscadorVentasActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Sistema.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Sistema.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Sistema.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sistema.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Sistema().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser Midate;
    private javax.swing.JButton btnActualizarCliente;
    private javax.swing.JButton btnActualizarConfig;
    private javax.swing.JButton btnActualizarProducto;
    private javax.swing.JButton btnActualizarProveedor;
    private javax.swing.JButton btnBuscadorVentas;
    private javax.swing.JButton btnClientes;
    private javax.swing.JButton btnConfig;
    private javax.swing.JButton btnEliminarCliente;
    private javax.swing.JButton btnEliminarProducto;
    private javax.swing.JButton btnEliminarProveedor;
    private javax.swing.JButton btnEliminarVenta;
    private javax.swing.JButton btnExcelProducto;
    private javax.swing.JButton btnGraficar;
    private javax.swing.JButton btnGuardarCliente;
    private javax.swing.JButton btnGuardarProducto;
    private javax.swing.JButton btnGuardarProvedor;
    private javax.swing.JButton btnImprimirVenta;
    private javax.swing.JButton btnLimpiarCliente;
    private javax.swing.JButton btnNuevaVenta;
    private javax.swing.JButton btnNuevoProducto;
    private javax.swing.JButton btnNuevoProveedor;
    private javax.swing.JButton btnPdfVentas;
    private javax.swing.JButton btnProducto;
    private javax.swing.JButton btnProveedor;
    private javax.swing.JButton btnUsuarios;
    private javax.swing.JButton btnVentas;
    private javax.swing.JComboBox<String> cbxProveedorProducto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane8;
    private javax.swing.JLabel labelTotalVenta;
    private javax.swing.JLabel labelVendedor;
    private javax.swing.JTable tableCliente;
    private javax.swing.JTable tableProducto;
    private javax.swing.JTable tableProveedor;
    private javax.swing.JTable tableVenta;
    private javax.swing.JTable tableVentas;
    private javax.swing.JTextField txtCantidadVenta;
    private javax.swing.JTextField txtCodigoProducto;
    private javax.swing.JTextField txtCodigoVenta;
    private javax.swing.JTextField txtDescripcionVenta;
    private javax.swing.JTextField txtDireccionCVenta;
    private javax.swing.JTextField txtDireccionCliente;
    private javax.swing.JTextField txtDireccionConfig;
    private javax.swing.JTextField txtDireccionProveedor;
    private javax.swing.JTextField txtDniCliente;
    private javax.swing.JTextField txtIdCliente;
    private javax.swing.JTextField txtIdConfig;
    private javax.swing.JTextField txtIdProducto;
    private javax.swing.JTextField txtIdProveedor;
    private javax.swing.JTextField txtIdVentas;
    private javax.swing.JTextField txtIdproducto;
    private javax.swing.JTextField txtNombreCliente;
    private javax.swing.JTextField txtNombreConfig;
    private javax.swing.JTextField txtNombreProducto;
    private javax.swing.JTextField txtNombreProveedor;
    private javax.swing.JTextField txtNombreVenta;
    private javax.swing.JTextField txtPrecioProducto;
    private javax.swing.JTextField txtPrecioVenta;
    private javax.swing.JTextField txtRazonCVenta;
    private javax.swing.JTextField txtRazonCliente;
    private javax.swing.JTextField txtRazonConfig;
    private javax.swing.JTextField txtRazonProveedor;
    private javax.swing.JTextField txtRucConfig;
    private javax.swing.JTextField txtRucProveedor;
    private javax.swing.JTextField txtRucVenta;
    private javax.swing.JTextField txtStockProducto;
    private javax.swing.JTextField txtStockVenta;
    private javax.swing.JTextField txtTelefonoCVenta;
    private javax.swing.JTextField txtTelefonoCliente;
    private javax.swing.JTextField txtTelefonoConfig;
    private javax.swing.JTextField txtTelefonoProveedor;
    private javax.swing.JTextField txtbuscadorVentas;
    // End of variables declaration//GEN-END:variables

    private void limpiarCliente() {
        txtIdCliente.setText("");
        txtDniCliente.setText("");
        txtNombreCliente.setText("");
        txtTelefonoCliente.setText("");
        txtDireccionCliente.setText("");
        txtRazonCliente.setText("");
    }

    private void limpiarProveedor() {
        txtIdProveedor.setText("");
        txtRucProveedor.setText("");
        txtNombreProveedor.setText("");
        txtTelefonoProveedor.setText("");
        txtDireccionProveedor.setText("");
        txtRazonProveedor.setText("");
    }

    private void limpiarProducto() {
        txtIdProducto.setText("");
        txtCodigoProducto.setText("");
        cbxProveedorProducto.setSelectedItem("");
        txtNombreProducto.setText("");
        txtStockProducto.setText("");
        txtPrecioProducto.setText("");
        txtIdVentas.setText("");
    }

    private void totalPagar() {
        totalPagar = 0.00;
        int numFila = tableVenta.getRowCount();
        for (int i = 0; i < numFila; i++) {
            double cal = Double.parseDouble(String.valueOf(tableVenta.getModel().getValueAt(i, 4)));
            totalPagar = totalPagar + cal;
        }
        labelTotalVenta.setText(String.format("%.2f", totalPagar));
    }

    private void limpiarVenta() {
        txtCodigoVenta.setText("");
        txtDescripcionVenta.setText("");
        txtCantidadVenta.setText("");
        txtStockVenta.setText("");
        txtPrecioVenta.setText("");
    }

    private void registrarVenta() {
        String cliente = txtNombreVenta.getText();
        String vendedor = labelVendedor.getText();
        double monto = totalPagar;
        v.setCliente(cliente);
        v.setVendedor(vendedor);
        v.setTotal(monto);
        v.setFecha(fechaActual);
        vDAO.registrarVenta(v);
    }

    private void registrarDetalle() {
        int id = vDAO.idVenta();
        for (int i = 0; i < tableVenta.getRowCount(); i++) {
            String cod = tableVenta.getValueAt(i, 0).toString();
            int cant = Integer.parseInt(tableVenta.getValueAt(i, 2).toString());
            double prec = Double.parseDouble(tableVenta.getValueAt(i, 3).toString());
            dv.setCod_pro(cod);
            dv.setCantidad(cant);
            dv.setPrecio(prec);
            dv.setId(id);
            vDAO.registrarDetalle(dv);
        }
    }

    private void actualizarStock() {
        for (int i = 0; i < tableVenta.getRowCount(); i++) {
            String cod = tableVenta.getValueAt(i, 0).toString();
            int cant = Integer.parseInt(tableVenta.getValueAt(i, 2).toString());
            pto = ptoDAO.buscarProducto(cod);
            int stockActual = pto.getStock() - cant;
            vDAO.actualizarStock(stockActual, cod);
        }
    }

    private void limpiartableVenta() {
        tmp = (DefaultTableModel) tableVenta.getModel();
        int fila = tableVenta.getRowCount();
        for (int i = 0; i < fila; i++) {
            tmp.removeRow(0);
        }
    }

    private void limpiarclienteVenta() {
        txtRucVenta.setText("");
        txtNombreVenta.setText("");
        txtTelefonoCVenta.setText("");
        txtDireccionCVenta.setText("");
        txtRazonCVenta.setText("");
        labelTotalVenta.setText("-----");
    }

    private void pdf() throws DocumentException, IOException, URISyntaxException {
        try {
            int id = vDAO.idVenta();
            Document doc = new Document();
            String pdfPath = "/pdf/venta" + id + ".pdf";
            URL pdfUrl = getClass().getResource(pdfPath);

            if (pdfUrl != null) {
                File file = new File(getClass().getResource(pdfPath).toURI());
                try (FileOutputStream archivo = new FileOutputStream(file)) {
                    PdfWriter.getInstance(doc, archivo);
                    doc.open();
                    Image img = Image.getInstance(getClass().getResource("/Img/SKULLRIDERS PDF.png"));

                    Paragraph fecha = new Paragraph();
                    Font negrita = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK);
                    fecha.add(Chunk.NEWLINE);
                    Date date = new Date();
                    fecha.add("Factura:" + id + "\n" + "Fecha: " + new SimpleDateFormat("dd-MM-yyyy").format(date) + "\n\n");

                    PdfPTable encabezado = new PdfPTable(4);
                    encabezado.setWidthPercentage(100);
                    encabezado.getDefaultCell().setBorder(0);
                    float[] columnaEncabezado = new float[]{20f, 30f, 70f, 40f};
                    encabezado.setWidths(columnaEncabezado);
                    encabezado.setHorizontalAlignment(Element.ALIGN_LEFT);

                    encabezado.addCell(img);

                    String ruc = txtRucConfig.getText();
                    String nom = txtNombreConfig.getText();
                    String tel = txtTelefonoConfig.getText();
                    String dir = txtDireccionConfig.getText();
                    String ras = txtRazonConfig.getText();

                    encabezado.addCell("");
                    encabezado.addCell("Ruc: " + ruc + "\nNombre: " + nom + "\nTelefono: " + tel + "\nDirección: " + dir + "\nRazón Social: " + ras);
                    encabezado.addCell(fecha);
                    doc.add(encabezado);

                    Paragraph cliente = new Paragraph();
                    cliente.add(Chunk.NEWLINE);
                    cliente.add("Datos de los clientes" + "\n\n");
                    doc.add(cliente);

                    //clientes
                    PdfPTable tablaCliente = new PdfPTable(4);
                    tablaCliente.setWidthPercentage(100);
                    tablaCliente.getDefaultCell().setBorder(0);
                    float[] columnaCliente = new float[]{20f, 35f, 30f, 60f};
                    tablaCliente.setWidths(columnaCliente);
                    tablaCliente.setHorizontalAlignment(Element.ALIGN_LEFT);
                    PdfPCell cliente1 = new PdfPCell(new Phrase("Dni/Ruc", negrita));
                    PdfPCell cliente2 = new PdfPCell(new Phrase("Nombre", negrita));
                    PdfPCell cliente3 = new PdfPCell(new Phrase("Telefono", negrita));
                    PdfPCell cliente4 = new PdfPCell(new Phrase("Dirección", negrita));
                    cliente1.setBorder(0);
                    cliente2.setBorder(0);
                    cliente3.setBorder(0);
                    cliente4.setBorder(0);
                    tablaCliente.addCell(cliente1);
                    tablaCliente.addCell(cliente2);
                    tablaCliente.addCell(cliente3);
                    tablaCliente.addCell(cliente4);
                    tablaCliente.addCell(txtRucVenta.getText());
                    tablaCliente.addCell(txtNombreVenta.getText());
                    tablaCliente.addCell(txtTelefonoCVenta.getText());
                    tablaCliente.addCell(txtDireccionCVenta.getText());
                    doc.add(tablaCliente);

                    doc.add(Chunk.NEWLINE);

                    //productos
                    PdfPTable tablaProductos = new PdfPTable(4);
                    tablaProductos.setWidthPercentage(100);
                    tablaProductos.getDefaultCell().setBorder(0);
                    float[] columnaProductos = new float[]{10f, 50f, 15f, 20f};
                    tablaProductos.setWidths(columnaProductos);
                    tablaProductos.setHorizontalAlignment(Element.ALIGN_LEFT);
                    PdfPCell productos1 = new PdfPCell(new Phrase("Cantidad", negrita));
                    PdfPCell productos2 = new PdfPCell(new Phrase("Descripción", negrita));
                    PdfPCell productos3 = new PdfPCell(new Phrase("Precio Unit.", negrita));
                    PdfPCell productos4 = new PdfPCell(new Phrase("Precio Total", negrita));
                    productos1.setBorder(0);
                    productos2.setBorder(0);
                    productos3.setBorder(0);
                    productos4.setBorder(0);
                    productos1.setBackgroundColor(BaseColor.GRAY);
                    productos2.setBackgroundColor(BaseColor.GRAY);
                    productos3.setBackgroundColor(BaseColor.GRAY);
                    productos4.setBackgroundColor(BaseColor.GRAY);
                    tablaProductos.addCell(productos1);
                    tablaProductos.addCell(productos2);
                    tablaProductos.addCell(productos3);
                    tablaProductos.addCell(productos4);
                    for (int i = 0; i < tableVenta.getRowCount(); i++) {
                        String pro = tableVenta.getValueAt(i, 1).toString();
                        String cant = tableVenta.getValueAt(i, 2).toString();
                        String prec = tableVenta.getValueAt(i, 3).toString();
                        String total = tableVenta.getValueAt(i, 4).toString();
                        tablaProductos.addCell(cant);
                        tablaProductos.addCell(pro);
                        tablaProductos.addCell(prec);
                        tablaProductos.addCell(total);
                    }
                    doc.add(tablaProductos);

                    Paragraph info = new Paragraph();
                    info.add(Chunk.NEWLINE);
                    info.add("Total a pagar: S/." + totalPagar);
                    info.setAlignment(Element.ALIGN_RIGHT);
                    doc.add(info);

                    PdfPTable tablaFirma = new PdfPTable(1);
                    tablaFirma.setWidthPercentage(100);
                    PdfPCell celdaFirma = new PdfPCell(new Paragraph("Cancelación y Firma\n\n\n\n" + " ----------------------------------"));
                    celdaFirma.setHorizontalAlignment(Element.ALIGN_CENTER);
                    celdaFirma.setBorder(Rectangle.NO_BORDER);
                    tablaFirma.addCell(celdaFirma);
                    for (int i = 0; i < 20; i++) {
                        PdfPTable filaVacia = new PdfPTable(1);
                        PdfPCell celdaVacia = new PdfPCell(new Paragraph(" "));
                        celdaVacia.setBorder(Rectangle.NO_BORDER);
                        filaVacia.addCell(celdaVacia);
                        doc.add(filaVacia);
                    }
                    doc.add(tablaFirma);

                    Paragraph mensaje = new Paragraph();
                    mensaje.add(Chunk.NEWLINE);
                    mensaje.add("Gracias por su compra!");
                    mensaje.setAlignment(Element.ALIGN_CENTER);
                    doc.add(mensaje);
                    doc.close();
                }
                Desktop.getDesktop().open(file);
            } else {
                System.out.println("No se pudo encontrar el archivo PDF.");
            }
        } catch (DocumentException | IOException e) {
            System.out.println(e.toString());
        }
    }
}
