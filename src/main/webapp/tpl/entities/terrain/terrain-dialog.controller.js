(function () {
    'use strict';

    angular
            .module('app')
            .controller('TerrainDialogController', TerrainDialogController)
            .controller('LotDialogController', LotDialogController);

    TerrainDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$uibModal', 'DataUtils', 'entity', 'Terrain', 'Unite', 'ProduitCategorie', 'terrains'];
    LotDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$uibModal', 'DataUtils', 'entity', 'Terrain', 'Unite', 'ProduitCategorie'];

    function TerrainDialogController($timeout, $scope, $stateParams, $uibModalInstance, $uibModal, DataUtils, entity, Terrain, Unite, ProduitCategorie, terrains) {
        var vm = this;

        vm.terrain = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.terrainparents = Terrain.query();
        vm.categories = ProduitCategorie.query();
        vm.unites = Unite.query();
        vm.terrains = terrains;



        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function save() {
            vm.isSaving = true;
            if (vm.terrain.id !== null) {
                Terrain.update(vm.terrain, onSaveSuccess, onSaveError);
            } else {
                Terrain.save(vm.terrain, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess(result) {
            $scope.$emit('tkbrApp:terrainUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError() {
            vm.isSaving = false;
        }




        function openCalendar(date) {
            vm.datePickerOpenStatus[date] = true;
        }

        vm.setMimage = function ($file, fieldName) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function (base64Data) {
                    $scope.$apply(function () {
                        vm.terrain[fieldName] = base64Data;
                        vm.terrain[fieldName + 'ContentType'] = $file.type;
                    });
                });
            }
        };

        vm.zoomColumn = function (lookupCtrl, lookupTemplate, fieldname, entity) {
            $uibModal.open({
                templateUrl: 'tpl/entities/' + lookupTemplate + '/' + lookupTemplate + '-dialog.html',
                controller: lookupCtrl + 'DialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: function () {
                        return entity;
                    }
                }
            }).result.then(function (item) {
                vm.terrain[fieldname] = item;
            }, function () {

            });
        };

        vm.editLot = function (entity, index) {
            $uibModal.open({
                templateUrl: 'tpl/entities/terrain/lot-dialog.html',
                controller: 'LotDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: function () {
                        return entity;
                    }
                }
            }).result.then(function (item) {
                vm.terrains[index] = item;
                vm.terrain = Terrain.get({id: $stateParams.id});
            }, function () {

            });
        };
        vm.delLot = function (entity, index) {
            $uibModal.open({
                templateUrl: 'tpl/entities/terrain/terrain-delete-dialog.html',
                controller: 'LotDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: function () {
                        return entity;
                    }
                }
            }).result.then(function () {
                vm.terrains.splice(index, 1);
                vm.terrain = Terrain.get({id: $stateParams.id});
            }, function () {

            });
        };
        vm.addLot = function () {
            $uibModal.open({
                templateUrl: 'tpl/entities/terrain/lot-dialog.html',
                controller: 'LotDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: function () {
                        return {
                            adresse: vm.terrain.adresse,
                            type: vm.terrain.type,
                            categorie: vm.terrain.categorie,
                            unite: vm.terrain.unite,
                            terrainParent: vm.terrain
                        };
                    }
                }
            }).result.then(function (item) {
                vm.terrains.push(item);
                vm.terrain = Terrain.get({id: $stateParams.id});
            }, function () {

            });
        };


    }

    function LotDialogController($timeout, $scope, $stateParams, $uibModalInstance, $uibModal, DataUtils, entity, Terrain, Unite, ProduitCategorie) {
        var vm = this;

        vm.terrain = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function save() {
            vm.isSaving = true;
            if (vm.terrain.id !== null) {
                Terrain.update(vm.terrain, onSaveSuccess, onSaveError);
            } else {
                Terrain.save(vm.terrain, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess(result) {
            $scope.$emit('tkbrApp:terrainUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError() {
            vm.isSaving = false;
        }

    }
})();
