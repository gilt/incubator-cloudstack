
-- PN: Temporary file to hold data model changes for LXC
-- support. Contents of this file will be moved to appropriate files
-- when ready to merge.


-- should this be in create-schema.sql???
INSERT IGNORE INTO `cloud`.`hypervisor_capabilities`(hypervisor_type, hypervisor_version, max_guests_limit, security_group_enabled) VALUES ('LXC', 'default', 50, 1);
ALTER TABLE `cloud`.`physical_network_traffic_types` ADD COLUMN `lxc_network_label` varchar(255) DEFAULT 'cloudbr0' COMMENT 'The network name label of the physical device dedicated to this traffic on a LXC host';

-- add LXC to configuration hypervisor list
UPDATE configuration SET value='KVM,XenServer,VMware,BareMetal,Ovm,LXC' WHERE name='hypervisor.list';

-- template builtin for lxc
INSERT INTO `cloud`.`vm_template` (id, unique_name, name, public, created, type, hvm, bits, account_id, url, checksum, display_text, enable_password, format, guest_os_id, featured, cross_zones, hypervisor_type, extractable)
    VALUES (10, 'centos63-x86_64-gilt', 'CentOS 6.3(64-bit) no GUI (LXC)', 1, now(), 'BUILTIN', 0, 64, 1, 'https://s3.amazonaws.com/pnguyen/public/centos63.tar.gz', '4928c590590a11e2bcfd0800200c9a66', 'CentOS 6.3(64-bit) Gilt (LXC)', 0, 'TAR', 112, 1, 1, 'LXC', 1);
